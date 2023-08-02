new Vue({
    el: '#app',
    data: {
        username: '',
        password: ''
    },
    methods: {
        async submitForm() {
            if(this.username && this.password) {
                try {
                    const response = await axios.post('/authenticate', {
                        username: this.username,
                        password: this.password
                    });
                    if (response.status === 200) {
                        const id_token = response.data.id_token;
                        sessionStorage.setItem('id_token', id_token);
                        window.location.href = '/main';
                    }
                } catch(error) {
                    console.log(error);
                    alert('사용자 인증실패');
                }
            } else {
                console.log("Please fill both fields");
            }
        }
    }
});
