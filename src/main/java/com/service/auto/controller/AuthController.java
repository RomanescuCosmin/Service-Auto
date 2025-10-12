package com.service.auto.controller;

import com.service.auto.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping
public class AuthController extends BaseController {


    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {

        if (error != null) {
            log.warn("Eroare autentificare: credentiale invalide");
            model.addAttribute("error", "Email sau parolă greșită.");
        }
        if (logout != null) {
            log.info("Utilizatorul a fost delogat cu succes.");
            model.addAttribute("message", "Ai fost delogat.");
        }

        return "login";
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@RequestParam String email,
                                      @RequestParam String nume,
                                      @RequestParam String password,
                                      @RequestParam String confirmPassword,
                                      Model model, RedirectAttributes redir) {
        if (!password.equals(confirmPassword)) {
            log.warn("Parolele nu coincid pentru {}", email);
            model.addAttribute("error", "Parolele nu coincid");
            return "register";
        }

        if (userService.findByEmail(email).isPresent()) {
            log.warn("Emailul {} este deja înregistrat", email);
            model.addAttribute("error", "Emailul este deja înregistrat");
            return "register";
        }

        User user = new User();
        user.setNume(nume);
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        userService.save(user);
        log.info("Utilizator nou înregistrat: {}", email);

        redir.addFlashAttribute("success", "Cont creat cu succes. Autentifică-te!");
        return "redirect:/login";
    }


    /**
     * Afișează formularul de resetare parolă.
     */
    @GetMapping("/forgot-password")
    public String forgotPasswordForm() {
        return "forgot-password";
    }

    /**
     * Primește emailul și trimite un link de resetare dacă userul există.
     */
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, Model model) {
        log.info("Cerere resetare parolă pentru email: {}", email);
        userService.findByEmail(email).ifPresentOrElse(user -> {
            String token = UUID.randomUUID().toString();
            userService.createResetTokenForUser(user, token);
            emailService.sendPasswordResetEmail(email, token);
            log.info("Link resetare trimis la {}", email);
        }, () -> log.warn("Emailul {} nu a fost găsit în sistem", email));

        model.addAttribute("message", "Un Email a fost trimis cu un link de resetare a parolei.");
        return "redirect:/";
    }

    /**
     * Afișează formularul pentru introducerea noii parole pe baza tokenului.
     */
    @GetMapping("/reset-password")
    public String showResetForm(@RequestParam String token, Model model) {
        log.debug("Acces resetare parolă cu token: {}", token);
        Optional<User> user = userService.validatePasswordResetToken(token);
        if (user.isEmpty()) {
            log.warn("Token invalid sau expirat: {}", token);
            model.addAttribute("error", "Token invalid sau expirat");
            return "forgot-password";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }

    /**
     * Primește parola nouă și salvează dacă tokenul este valid.
     */
    @PostMapping("/reset-password")
    public String processReset(@RequestParam String token,
                               @RequestParam String password,
                               @RequestParam String confirmPassword,
                               Model model) {
        if (!password.equals(confirmPassword)) {
            log.warn("Parolele nu se potrivesc pentru tokenul {}", token);
            model.addAttribute("error", "Parolele nu se potrivesc");
            model.addAttribute("token", token);
            return "reset-password";
        }

        Optional<User> userOpt = userService.validatePasswordResetToken(token);
        if (userOpt.isEmpty()) {
            log.warn("Token invalid sau expirat: {}", token);
            model.addAttribute("error", "Token invalid sau expirat");
            return "forgot-password";
        }

        userService.updatePassword(userOpt.get(), passwordEncoder.encode(password));
        log.info("Parola a fost resetată pentru utilizatorul {}", userOpt.get().getEmail());
        return "redirect:/login?resetSuccess";
    }

}
