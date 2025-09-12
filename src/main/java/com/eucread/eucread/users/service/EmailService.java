package com.eucread.eucread.users.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import com.eucread.eucread.exception.BusinessException;
import com.eucread.eucread.users.auth.exception.AuthErrorCode;
import com.eucread.eucread.users.dto.request.EmailAuthReq;
import com.eucread.eucread.users.entity.User;
import com.eucread.eucread.users.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender javaMailSender;
	private final StringRedisTemplate redisTemplate;
	private final UserRepository userRepository;

	private static final Duration EMAIL_CODE_TTL = Duration.ofMinutes(10);

	public void sendEmail(String email) {

		String code = generateCode();

		ValueOperations<String, String> ops = redisTemplate.opsForValue();
		ops.set(email, code, EMAIL_CODE_TTL);

		try {
			ClassPathResource resource = new ClassPathResource("templates/email-template.html");
			String htmlTemplate = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

			String htmlContent = htmlTemplate.replace("{{CODE}}", code);

			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setTo(email);
			helper.setSubject("[Eucread] 이메일 인증 코드");
			helper.setText(htmlContent, true);

			javaMailSender.send(message);

		} catch (IOException | MessagingException e) {
			throw new RuntimeException("이메일 전송 실패", e);
		}

	}

	@Transactional
	public boolean authEmail(EmailAuthReq request) {
		String email = request.getEmail();
		String inputCode = request.getCode();

		ValueOperations<String, String> ops = redisTemplate.opsForValue();
		String savedCode = ops.get(email);

		if (savedCode == null) {
			throw new BusinessException(AuthErrorCode.AUTH_CODE_EXPIRED);
		}

		if (!savedCode.equals(inputCode)) {
			throw new BusinessException(AuthErrorCode.AUTH_CODE_INVALID);
		}

		redisTemplate.delete(email);

		redisTemplate.opsForValue().set("auth:email:" + request.getEmail(), "true", 10, TimeUnit.MINUTES);

		return true;
	}

	private String generateCode() {
		Random random = new Random();
		int code = 100000 + random.nextInt(900000);
		return String.valueOf(code);
	}
}
