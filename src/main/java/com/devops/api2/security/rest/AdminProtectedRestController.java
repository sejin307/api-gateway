package com.devops.api2.security.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * admin role 사용자
 * sejin
 */
@RestController
@RequestMapping("/api")
public class AdminProtectedRestController {

   @GetMapping("/hiddenmessage")
   public ResponseEntity<HiddenMessage> getAdminProtectedGreeting() {
      return ResponseEntity.ok(new HiddenMessage("Only Admin MessageView!"));
   }

   private static class HiddenMessage {

      private final String message;

      private HiddenMessage(String message) {
         this.message = message;
      }

      public String getMessage() {
         return message;
      }
   }

}
