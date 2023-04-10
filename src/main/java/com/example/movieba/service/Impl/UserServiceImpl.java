package com.example.movieba.service.Impl;

import com.example.movieba.entities.Company;
import com.example.movieba.entities.Role;
import com.example.movieba.entities.User;
import com.example.movieba.exception.BusinessCode;
import com.example.movieba.exception.BusinessException;
import com.example.movieba.mapper.UserMapper;
import com.example.movieba.model.request.user.PasswordRequest;
import com.example.movieba.model.request.user.ProfileRequest;
import com.example.movieba.model.request.user.UserInfoRequest;
import com.example.movieba.model.response.UserInfoResponse;
import com.example.movieba.repository.CompanyRepository;
import com.example.movieba.repository.RoleRepository;
import com.example.movieba.repository.UserRepository;
import com.example.movieba.security.JwtService;
import com.example.movieba.service.UserService;
import com.example.movieba.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final RoleRepository roleRepository;

    private final JwtService jwtService;

    @Override
    public String createUser(UserInfoRequest infoRequest) {
        String message = "";
        Optional<User> userCheck = userRepository.findByUserNameOrEmail(infoRequest.getUserName(), infoRequest.getEmail());
        if (userCheck.isEmpty()){
            User user = userMapper.to(infoRequest);
            Set<Role> roles = roleRepository.findByRoleIdIn(Arrays.asList(1L));
            user.setRoles(roles);
            user.setTotal(0L);
            userRepository.save(user);
            message = "CREATE-SUCCESS";
        }else{
            message = "CREATE-FAIL";
        }
        return message;
    }

    @Override
    public String createCompany(UserInfoRequest infoRequest) {
        String message = "";
        Optional<User> userCheck = userRepository.findByUserNameOrEmail(infoRequest.getUserName(), infoRequest.getEmail());
        if (userCheck.isEmpty()){
            User user = userMapper.to(infoRequest);
            Set<Role> roles = roleRepository.findByRoleIdIn(Arrays.asList(3L));
            user.setRoles(roles);
            userRepository.save(user);
            message = "CREATE-SUCCESS";
        }else{
            message = "CREATE-FAIL";
        }
        return message;
    }

    @Override
    public UserInfoResponse infoUser(HttpServletRequest request) {
        String token = JwtUtils.getToken(request);

        String userName = jwtService.extractUsername(token);

        Optional<User> user = userRepository.findByUserName(userName);
        user.orElseThrow(()->new BusinessException(BusinessCode.USER_NOT_FOUND));

        return userMapper.from(user.get());
    }

    @Override
    public UserInfoResponse infoById(long id) {
        Optional<User> findUser = userRepository.findById(id);
        findUser.orElseThrow(()->new BusinessException(BusinessCode.USER_NOT_FOUND));
        if (findUser.get() != null){
            return userMapper.from(findUser.get());
        }
        return null;
    }

    @Override
    public String updatePassword(HttpServletRequest request, PasswordRequest passwordRequest) {
        String message = "";

        String token = JwtUtils.getToken(request);

        String userName = jwtService.extractUsername(token);

        Optional<User> user = userRepository.findByUserName(userName);
        user.orElseThrow(()->new BusinessException(BusinessCode.USER_NOT_FOUND));

        if (passwordRequest.getNewPassword().equalsIgnoreCase(passwordRequest.getNewPasswordT())){
            User userUpdate = user.get();
            userUpdate.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
            userRepository.save(userUpdate);
            message = "PASSWORD-SUCCESS";
        }else{
            message = "NOT-EQUAL";
        }

        return message;
    }

    @Override
    public UserInfoResponse updateProfile(HttpServletRequest request, ProfileRequest profileRequest) {
        String token = JwtUtils.getToken(request);

        String userName = jwtService.extractUsername(token);

        Optional<User> user = userRepository.findByUserName(userName);
        user.orElseThrow(()->new BusinessException(BusinessCode.USER_NOT_FOUND));

        if (user.get() != null){
            User userUpdate = user.get();
            userUpdate.setAddress(profileRequest.getAddress());
            userUpdate.setFullName(profileRequest.getFullName());
            userRepository.save(userUpdate);
            return userMapper.from(userUpdate);
        }
        return userMapper.from(user.get());
    }

    @Override
    public List<String> roleUser(HttpServletRequest request) {
        String token = JwtUtils.getToken(request);

        String userName = jwtService.extractUsername(token);

        Optional<User> user = userRepository.findByUserName(userName);
        user.orElseThrow(()->new BusinessException(BusinessCode.USER_NOT_FOUND));

        List<String> findRole = user.get().getRoles().stream().map(t-> t.getRoleName()).collect(Collectors.toList());
        return findRole;

    }

    @Override
    public Page<UserInfoResponse> getAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable.previousOrFirst());
        return users.map(userMapper::from);
    }

    @Override
    public List<UserInfoResponse> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(t->{
            UserInfoResponse userInfoResponse = userMapper.from(t);
            return userInfoResponse;
        }).collect(Collectors.toList());
    }

    @Override
    public int getTotalUser() {
        return userRepository.findAll().size();
    }

    @Override
    public void addCompany(long id, long role) {
        Optional<User> user = userRepository.findById(id);
        user.orElseThrow(()-> new BusinessException(BusinessCode.USER_NOT_FOUND));
        Set<Role> roles = roleRepository.findByRoleIdIn(Arrays.asList(role));
        Optional<Company> findCompany = companyRepository.findByUser(user.get());

        if (user.get()!=null && findCompany.get() == null){
            User userUpdate = user.get();
            userUpdate.setRoles(roles);
            userRepository.save(userUpdate);
            Company company = new Company();
            company.setUser(user.get());
            companyRepository.save(company);
        }
    }

    @Override
    public void changeStatusAccount(long id, long status) {
        Optional<User> user = userRepository.findById(id);
        user.orElseThrow(()-> new BusinessException(BusinessCode.USER_NOT_FOUND));
        if (user.get()!= null){
            User userUpdate = user.get();
            userUpdate.setStatus((int) status);
            userRepository.save(userUpdate);
        }
    }
}
