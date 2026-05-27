package hospital.medflow.service;

import hospital.medflow.config.CustomUserDetails;
import hospital.medflow.config.security.JwtUtils;
import hospital.medflow.criteria.BaseCriteria;
import hospital.medflow.criteria.DataList;
import hospital.medflow.dto.login.LoginRequest;
import hospital.medflow.dto.login.LoginResponse;
import hospital.medflow.dto.login.TokenDto;
import hospital.medflow.dto.user.ChangePasswordDto;
import hospital.medflow.dto.user.UserCreateDto;
import hospital.medflow.dto.user.UserDto;
import hospital.medflow.dto.user.UserUpdateDto;
import hospital.medflow.mapper.UserMapper;
import hospital.medflow.model.User;
import hospital.medflow.repository.UserRepository;
import hospital.medflow.service.base.AbstractService;
import hospital.medflow.service.base.CRUDService;
import hospital.medflow.utils.ErrorConstants;
import hospital.medflow.utils.SecurityUtils;
import hospital.medflow.validator.UserValidator;
import io.jsonwebtoken.Claims;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService extends AbstractService<UserRepository, UserMapper, UserValidator>
        implements CRUDService<UserDto, UserUpdateDto, UserCreateDto, BaseCriteria, String> {

    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, UserMapper mapper, UserValidator validator, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        super(repository, mapper, validator);
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public DataList<List<UserDto>> getAll(BaseCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());
        Page<User> bookings = repository.findAllUsers(pageable, criteria.getSearch());
        List<UserDto> dto = mapper.toDto(bookings.getContent());
        return DataList.<List<UserDto>>builder()
                .data(dto)
                .totalPages(bookings.getTotalPages())
                .allElements(bookings.getTotalElements())
                .build();
    }

    @Override
    public UserDto get(String id) {
        User user = validator.existAndGet(id);
        return mapper.toDto(user);
    }

    @Override
    public UserDto create(UserCreateDto dto) {
        validator.existUsername(dto.getUsername());
        User user = mapper.fromDto(dto);
        return mapper.toDto(repository.save(user));
    }

    @Override
    public UserDto update(String id, UserUpdateDto dto) {
        validator.existUsername(id, dto.getUsername());
        User user = validator.existAndGet(id);
        mapper.fromDto(dto, user);
        return mapper.toDto(repository.save(user));
    }

    @Override
    public void delete(String id) {
        User user = validator.existAndGet(id);
        user.setDeleted(true);
        repository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        User user = validator.existsUsernameAndGet(request.getUsername());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException(ErrorConstants.BAD_CREDENTIALS);
        }

        TokenDto accessToken = jwtUtils.generateAccessToken(user, new HashMap<>());
        TokenDto refreshToken = jwtUtils.generateRefreshToken(user);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public LoginResponse refreshToken(String token) {
        Claims claims = jwtUtils.exractClaims(token);
        User user = validator.existsUsernameAndGet(claims.getSubject());

        TokenDto accessToken = jwtUtils.generateAccessToken(user, new HashMap<>());
        TokenDto refreshToken = jwtUtils.generateRefreshToken(user);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void changePassword(ChangePasswordDto dto) {
        CustomUserDetails sessionUser = SecurityUtils.sessionUser();
        validator.validateChangePassword(dto, sessionUser);
        User user = validator.existAndGet(sessionUser.getId());
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        repository.save(user);
    }

    public UserDetails findByUsername(String username) {
        User user = validator.existsUsernameAndGet(username);
        return new CustomUserDetails(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), user.getSuperAdmin());
    }
}
