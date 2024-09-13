package fbc.userservice.controller;

import fbc.userservice.dto.UserDto;
import fbc.userservice.service.UserService;
import fbc.userservice.vo.Greeting;
import fbc.userservice.vo.RequestUser;
import fbc.userservice.vo.ResponseUser;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.userservice.controller
 * @fileName : UserController
 * @date : 24. 9. 9.
 * @description :
 * ===========================================================
 */
@RestController
@RequestMapping("/")
public class UserController {
    private Environment env;
    private UserService userService;

    @Autowired
    private Greeting greeting;

    @Autowired
    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @Operation(summary = "Health check API", description = "Health check를 위한 API (포트 및 Token Secret 정보 확인 가능)")
    @GetMapping("/health-check")
//    @Timed(value="users.status", longTask = true)
    public String status() {
        return String.format("It's Working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", gateway ip(env)=" + env.getProperty("gateway.ip")
//                + ", gateway ip(value)=" + greeting.getIp()
                + ", message=" + env.getProperty("greeting.message")
//                + ", token secret=" + greeting.getSecret()
                + ", token expiration time=" + env.getProperty("token.expiration_time"));
    }

    @Operation(summary = "환영 메시지 출력 API", description = "Welcome message를 출력하기 위한 API")
    @GetMapping("/welcome")
//    @Timed(value="users.welcome", longTask = true)
    public String welcome(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("users.welcome ip:" + request.getRemoteAddr() +
                "," + request.getRemoteHost() +
                "," + request.getRequestURI() +
                "," + request.getRequestURL());
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(user, UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }
}
