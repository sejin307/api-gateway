<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="bizwell.xclick.gw.mail.MailConstants"%>
<%@ page import="bizwell.xclick.gw.acl.vo.UserVO"%>
<%@page import="bizwell.xclick.utils.HttpUtil"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="java.io.ByteArrayInputStream"%>
<%@ page import="java.io.ByteArrayOutputStream"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="java.io.OutputStream"%>
<%@ page import="java.math.BigInteger"%>
<%@ page import="java.security.spec.AlgorithmParameterSpec"%>
<%@ page import="javax.crypto.Cipher"%>
<%@ page import="javax.crypto.SecretKey"%>
<%@ page import="javax.crypto.spec.IvParameterSpec"%>
<%@ page import="javax.crypto.spec.SecretKeySpec"%>
<%@ page import="javax.mail.internet.MimeUtility"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="java.io.OutputStreamWriter"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.net.SocketTimeoutException"%>
<%@ page import="java.text.ParseException"%>
<%@ page import="org.json.simple.parser.JSONParser"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONValue"%>
<%@ page import="java.net.HttpURLConnection"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="bizwell.xclick.cef.CefConstants"%>
<%@ page import="bizwell.framework.config.ConfigServiceManager"%>
<%@ page import="bizwell.xclick.gw.acl.service.CompanyService"%>
<%@ page import="bizwell.xclick.gw.acl.vo.CompanyVO"%>
<%@ page import="bizwell.xclick.utils.*"%>
<%@ include file="/portal/common/common.jsp" %>
<%
    //portlet은 아니고 type_a에 include 방식
    //portlet으로 쓰이려면 channel, preference 등등 추가적 코딩이 필요함
%>
<%!

    protected static final String CRYPTO_LEGACY_KEY = ConfigServiceManager.getInstance().getValue("xclick-cef", "//common/aes/key");
    protected static final String CRYPTO_LEGACY_IV  = ConfigServiceManager.getInstance().getValue("xclick-cef", "//common/aes/iv");
    protected static final byte[] key = new BigInteger(stringToHex(CRYPTO_LEGACY_KEY),16).toByteArray();
    protected static final byte[] iv  = new BigInteger(stringToHex(CRYPTO_LEGACY_IV),16).toByteArray();

    //암호화
    public static String encrypt(String plainText) throws Exception{
        byte [] encData = null;
        try{
            byte[] key 	= new BigInteger(stringToHex(CRYPTO_LEGACY_KEY),16).toByteArray();
            byte[] iv 	= new BigInteger(stringToHex(CRYPTO_LEGACY_IV),16).toByteArray();

            SecretKey sk = new SecretKeySpec(key, "AES");
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, sk ,paramSpec);

            encData = cipher.doFinal(plainText.getBytes("UTF-8"));
        }catch (Exception e) {
            // TODO: handle exception
        }

        return new String (base64Encode(encData));
    }

    //복호화
    public static String decrypt(String encryptedStr){
        String decryptedStr = "";
        try{
            byte[] key 	= new BigInteger(stringToHex(CRYPTO_LEGACY_KEY),16).toByteArray();
            byte[] iv 	= new BigInteger(stringToHex(CRYPTO_LEGACY_IV),16).toByteArray();

            SecretKey sk = new SecretKeySpec(key, "AES");
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, sk ,paramSpec);

            byte [] decByteData = base64Decode(encryptedStr.getBytes());

            byte[] decData = cipher.doFinal(decByteData);
            decryptedStr = new String(decData,"UTF-8");
        }catch (Exception e) {
            // TODO: handle exception
        }

        return decryptedStr;
    }

    //String -> hex 변환
    public static String stringToHex(String s) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            result += String.format("%02X", (int) s.charAt(i));
        }
        return result;
    }


    //BASE64 ENCODE
    public static byte[ ] base64Encode( byte[ ] b ) throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream( );
        OutputStream b64os = MimeUtility.encode( baos, "base64" );
        b64os.write( b );
        b64os.close( );
        return baos.toByteArray( );
    }

    //BASE64 DECODE
    public static byte[ ] base64Decode( byte[ ] b ) throws Exception{
        ByteArrayInputStream bais = new ByteArrayInputStream( b );
        InputStream b64is = MimeUtility.decode( bais, "base64" );
        byte[ ] tmp = new byte[ b.length ];
        int n = b64is.read( tmp );
        byte[ ] res = new byte[ n ];
        System.arraycopy( tmp, 0, res, 0, n );
        return res;
    }

    private JSONObject connectMis(String callUrl) throws Exception {
        PrintWriter postReq = null;
        BufferedReader postRes = null;
        String resultJson = null;
        StringBuilder resultBuffer = new StringBuilder();
        HttpURLConnection httpCon = null;
        JSONObject jsonObj = null;
        try {
            URL connectUrl = new URL(callUrl);
            InputStream is = null;
            httpCon = (HttpURLConnection)connectUrl.openConnection();
            httpCon.setRequestMethod("GET");
            httpCon.setDoOutput(true);
            httpCon.setUseCaches(false);
            httpCon.setConnectTimeout(1000); //TIMEOUT 5초 설정
            is = httpCon.getInputStream();

            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            jsonObj = (JSONObject)JSONValue.parse(isr);

            // JSONObject 수신
            //postRes = new BufferedReader(new InputStreamReader(httpCon.getInputStream(), "UTF-8"));
            //while ((resultJson = postRes.readLine()) != null){
            //		resultBuffer.append(resultJson);
            //}
        }catch(SocketTimeoutException e){
            e.printStackTrace();
            throw e;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            try{
                if(httpCon != null){
                    httpCon.disconnect();
                }
            }catch(Exception e){
            }
        }
        return jsonObj;
    }
%>
<%
    int unReadMailCnt = 0;
    int appTodoCnt = 0;
    int todayShareCnt = 0;
    int myTodoCnt = 0;

    JspController jspController = new JspController();
    jspController.setFacade("PortalFacade");
    jspController.setCommand("viewUserSummaryInfo");
    jspController.addParam("mailBoxId", MailConstants.MAIL_MAILBOX_INBOX);
    //[아이티센] 2021-12-20 / DEV_310 / bY mYMiNKi / 메신저 관련 패치
// 	jspController.addParam("todayShareQry", false); //공유일정 조회옵션!
    jspController.addParam("todayShareQry", true); //공유일정 조회옵션!
    jspController.addParam("usrQry", false); //사용자 조회옵션! - 세션정보에 없는정보 조회시는 true로 설정할 것!
    jspController.doMain(request,response);

    SvrData svrData = jspController.getSvrData();

    UserVO userVO = (UserVO)svrData.getObject("userVO", new UserVO());
    unReadMailCnt = svrData.getInt("unReadMailCnt", 0);
    appTodoCnt 	  = svrData.getInt("appTodoCnt", 0);
    todayShareCnt = svrData.getInt("todayShareCnt", 0);
    myTodoCnt = svrData.getInt("myTodoCnt", 0);


    //###################################################################
    //[아이티센] 2021-12-16 / DEV_306 / by rs_119 / 링크서비스 포틀릿 SLO로그인연동
    jspController.setFacade("CefPortalFacade");
    jspController.setCommand("isHasBURoleAuth");
    jspController.doMain(request,response);

    SvrData cefSvrData = jspController.getSvrData();
    boolean isHasCefPnsBuRole = cefSvrData.getBoolean("isHasCefPnsBuRole",false);
    boolean isHasCefEntBuRole = cefSvrData.getBoolean("isHasCefEntBuRole",false);

    //my_work_menu 커스텀 css
    String cef_myWorkMenu_CustomCss = "";
    if(_user.isCefContractWorker()) cef_myWorkMenu_CustomCss = "cef_myWorkMenu_1"; //my_work_menu 1개
    if(isHasCefEntBuRole && !isHasCefPnsBuRole) cef_myWorkMenu_CustomCss = "cef_myWorkMenu_2"; //my_work_menu 2개
    //###################################################################
    // [아이티센] 2022-11-18 / DEV_496 / by mingyu / 포탈 연차정보표기 - 연차정보
    //jspController.setFacade("CefPortalFacade");
    //jspController.setCommand("selectPMSVactnInfo");
    //jspController.doMain(request,response);
    //String vctnMsg = jspController.getSvrData().getString("vctnMsg","");



    //#####################################################################################################################################
    //포탈 연차표기 방식 변경( DBLINK > REST API CALL) / 231102 sejin307
    String companyId = "";
    String legacyCompanyCode = "";
    CompanyVO companyVO = CompanyService.getCompanyInfo(_user.getCompanyId());
    if(companyVO != null) legacyCompanyCode = companyVO.getLegacyCompanyCode();

    JSONObject vactnInfoJson = null;
    String strApiTrnRslt = "";
    String callUrl = ConfigServiceManager.getInstance().getValue("xclick-cef", "//legacy/mis/vacn-info-url");
    String plainStrParam = "";
    String encodeStrParam = "";
    JSONObject mApiRespData = new JSONObject();
    JSONObject mParser = new JSONObject();

    String nowVactn = MessageServiceManager.getInstance().getValue("CEF-080017", _user.getLocale());
    String useVactn = MessageServiceManager.getInstance().getValue("CEF-080018", _user.getLocale());
    String remainVactn = MessageServiceManager.getInstance().getValue("CEF-080019", _user.getLocale());
    String vctnMsg = "";
    try{
        // 전송
        plainStrParam = "cdCorp=" + legacyCompanyCode + "&" + "sabun=" + _user.getSabun();
        encodeStrParam = encrypt(plainStrParam);
        callUrl += "DATA="+java.net.URLEncoder.encode(encodeStrParam,"UTF8");
        vactnInfoJson = connectMis(callUrl);

        //조회된 연차데이터가 있을때만 표기함. 없으면 메시지 표기x
        if(vactnInfoJson != null && "0".equals(vactnInfoJson.get("status"))){
            vctnMsg = (new StringBuilder(String.valueOf(nowVactn))).append("&nbsp&nbsp").append(vactnInfoJson.get("createCnt")).append("&nbsp&nbsp&nbsp|&nbsp&nbsp&nbsp").append(useVactn).append("&nbsp&nbsp").append(vactnInfoJson.get("useCnt")).append("&nbsp&nbsp&nbsp|&nbsp&nbsp&nbsp").append(remainVactn).append("&nbsp&nbsp").append(vactnInfoJson.get("remainCnt")).toString();
        }
    }catch (SocketTimeoutException e) {
        e.printStackTrace();
    }catch (ParseException e) {
        e.printStackTrace();
    }catch (Exception e) {
        e.printStackTrace();
    }
    //#####################################################################################################################################

%>
<script type="text/javascript">
    function goMenuAction(topMenuId, leftmenuId, param){
        portalMenuAction(topMenuId, leftmenuId, param);
    }

    function goConfig(){
        if(xClickTop.window.triggerExtMenu && isFunction(xClickTop.window.triggerExtMenu)){
            xClickTop.window.triggerExtMenu('CONFIG');
        }
    }
    toggleTooltip = function(obj) {
        var tooltip = $(obj).find("div.tooltip_conts");
        var disp = tooltip.css('display')

        $('.icon_ma_setting').css('text-indent','0');

        if(disp==''||disp=='none') tooltip.css('display','inline-block');
        else tooltip.css('display','none');

        tooltip.html(jQuery.getMessage('COMMON-000180')); // 사용자환경설정
    }

    //##################################################################
    //[아이티센] 2023-01-03 / DEV_503 / by psd / 미결전표 카운트 및 전자전표 SLO로그인 처리
    //cef_portal_getPMSCorpCardUnfinishedCnt는 미사용
    //[아이티센] 2021-12-16 / DEV_305 / by rs_119 / MS법인카드미처리건수 포틀릿 연동
    //법인카드미처리 건수 가져오기
    /* function cef_portal_getPMSCorpCardUnfinishedCnt(){
        if($("#cefPMSCorpCardUnfinishedCnt").length > 0){
            var url = _webapp+ "/AjaxController";
            var param = {
                facade :"CefPortalFacade",
                command : "getPMSCorpCardInfoAjax",
                ajax_result_type : "JSON",
            };
            var callbackFn = function(data){
                $("#cefPMSCorpCardUnfinishedCnt").html(data.result);
            }
            ajaxJsonData(param, callbackFn, null, false);
        }
    } */

    //[아이티센] 2023-01-03 / DEV_503 / by psd / 미결전표 카운트 및 전자전표 SLO로그인 처리
    //미결전표 건수 요청
    function getCefTodoCntforAjax(){
        var params = {
            facade 		: 	"CefPortalFacade",
            command		: 	"getCefTodoCntforAjax",
        }
        ajaxJsonData(params, function(rtnData){
            $('#cefAppCnt').text(isNullStr(rtnData.corpCardAppCnt) ? "0" : rtnData.corpCardAppCnt);//법인카드 결재
            $('#cefPMSCorpCardUnfinishedCnt').text(isNullStr(rtnData.corpCardTodoCnt) ? "0" : rtnData.corpCardTodoCnt);//법인카드 미처리
        }, false, true);

    }
    //법인카드미처 클릭시 센웍스로 이동
    function goCefCorpCard(){
        openCefEtcSystemPop('PNS_PMS');
    }

    jQuery(document).ready(function () {
        //[아이티센] 2023-01-03 / DEV_503 / by psd / 미결전표 카운트 및 전자전표 SLO로그인 처리
        //cef_portal_getPMSCorpCardUnfinishedCnt는 미사용
        <%-- <% if(!isHasCefEntBuRole){ %>
            cef_portal_getPMSCorpCardUnfinishedCnt();
        <% } %> --%>
        getCefTodoCntforAjax();
    });
    //##################################################################

</script>
<%// [아이티센] 2022-11-18 / DEV_496 / by mingyu / 포탈 연차정보표기 - 사용자 정보 높이 변경 %>
<div class="my_info" style="height: 47px;">
    <div class="my_photo">
        <div class="profile_photo photo_med">
            <%
                if(!StringUtils.isEmpty(_user.getPhotoFileId())){
            %>
            <img src="<%=_webapp%>/DownController?fileId=<%=_user.getPhotoFileId() %>" onerror='this.src="<%=_cmDir%>/image/common/img_profile_sm.png<%=_cacheInfo%>"' />
            <%
            } else {
            %>
            <img src="<%=_cmDir%>/image/common/img_profile_sm.png<%=_cacheInfo%>" >
            <%
                }
            %>
        </div>
        <button type="button" class="eb_btn icon_btn icon_ma_setting btn_tooltip" onclick="goConfig()" onmouseover="toggleTooltip(this);" onmouseout="toggleTooltip(this);"><div class="tooltip_conts" style="display:none"></div></button>
    </div>
    <div class="my_name">
        <div class="name"><strong><%=_user.getUserName() %></strong><xclick:message msgId="COMMON-000272" /><i>!</i></div>
        <%
            //[아이티센] 2022-02-21 / DEV_457 / bY mYMiNKi / 포탈 메인 이름 > 직위 나오는 부분 직무로 나오게 변경
            //관리자 설정의 대표직함으로 변경__________[START]
        %>
        <div class="division"><%=_user.getDeptName() %> ｜ <%=_user.getRepTitleName() %></div>
        <%
            //관리자 설정의 대표직함으로 변경__________[ END ]
        %>
    </div>
</div>
<%// [아이티센] 2022-11-18 / DEV_496 / by mingyu / 포탈 연차정보표기 - 연차정보 %>
<div class="my_vcbtn" style="padding-left: 25px;">
    <a href="#" onclick="openCefEtcSystemPop('MIS');" style="color: white; font-size: 14px;"><%=vctnMsg %></a>
</div>
<%//[아이티센] 2021-11-04 / DEV_062 / by rs_119 / 계약직 계정 관리 / 메일만 사용 %>
<%// [아이티센] 2022-11-18 / DEV_496 / by mingyu / 포탈 연차정보표기 - padding 변경 %>
<%// <div class="my_work_menu"> %>
<div class="my_work_menu <%=cef_myWorkMenu_CustomCss%>" style="padding: 22px 0 39px 0;">
    <ul>
        <li>
            <button type="button" class="eb_btn icon_btn icon_ma_mail" onclick="goMenuAction('MAIL', 'MAIL_INBOX', '')"><span class="news"><%=unReadMailCnt %></span></button>
            <p class="menuname" onclick="goMenuAction('MAIL', 'MAIL_INBOX', '')"><xclick:message msgId="MAIL-000240" /><!-- 메일함 --></p>
        </li>
        <%//[아이티센] 2021-11-04 / DEV_062 / by rs_119 / 계약직 계정 관리 / 메일만 사용 %>
        <%if(!_user.isCefContractWorker()){ %>
        <li>
            <%//[아이티센] 2021-12-30 / DEV_350 / bY mYMINK / 임시로 흐리게만듬 메인 포털 결재
                // 2022-02-20 / rs_119 / 다시 진하게 변경
                //수정_____________[START]
            %>
            <button type="button" class="eb_btn icon_btn icon_ma_pencil" onclick="goMenuAction('APP', 'TODOLIST', '')"><span class="news"><%=appTodoCnt %></span></button>
            <p class="menuname" onclick="goMenuAction('APP', 'TODOLIST', '')"><xclick:message msgId="APP-000084" /><!-- 결재 --></p>
        </li>
        <%
            //수정_____________[ END ]
        %>
        <%//############################################################################ %>
        <%//[아이티센] 2021-12-16 / DEV_305 / by rs_119 / MS법인카드미처리건수 포틀릿 연동 %>
        <%-- <li>
            <button type="button" class="eb_btn icon_btn icon_ma_doc" onclick="goMenuAction('WORKMGR', 'WORKMGR_TODOLIST', '')"><span class="news"><%=myTodoCnt %></span></button>
            <p class="menuname" onclick="goMenuAction('WORKMGR', 'WORKMGR_TODOLIST', '')"><xclick:message msgId="BPM-000208" /><!-- 대기업무 --></p>
        </li>--%>
        <!-- //[아이티센] 2023-01-03 / DEV_503 / by psd / 미결전표 카운트 및 전자전표 SLO로그인 처리
                권한삭제 -->
        <%-- <% if(!isHasCefEntBuRole || isHasCefPnsBuRole){ %> --%>
        <li>
            <button type="button" class="eb_btn icon_btn icon_ma_doc" onclick="openCefEtcSystemPop('ELSCARD');"><span id="cefPMSCorpCardUnfinishedCnt" class="news">0</span></button>
            <p class="menuname" onclick="openCefEtcSystemPop('ELSCARD');">법인카드<br>미처리<!-- 법인카드 미처리 <xclick:message msgId="CEF-084040" /> --></p>
        </li>
        <%-- <%} %> --%>
        <%//############################################################################ %>
        <!-- //[아이티센] 2023-01-03 / DEV_503 / by psd / 미결전표 카운트 및 전자전표 SLO로그인 처리 -->
        <!-- // 230109 김세진 변경-->
        <li>
            <button type="button" class="eb_btn icon_btn icon_ma_doc" onclick="openCefEtcSystemPop('ELSAPP');"><span id="cefAppCnt" class="news">0</span></button>
            <p class="menuname" onclick="openCefEtcSystemPop('ELSAPP');">법인카드<br>결재<!-- 법인카드 결재 --></p>
        </li>
        <%} %>

    </ul>
</div>
