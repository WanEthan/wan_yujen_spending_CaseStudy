package com.example.logindemo2.service.iml;

import com.example.logindemo2.dto.UserDTO;
//import com.example.logindemo2.model.Role;
import com.example.logindemo2.model.User;
//import com.example.logindemo2.model.UserPrincipal;
import com.example.logindemo2.repository.UserRepository;
import com.example.logindemo2.service.CustomUserDetails;
//import com.example.logindemo2.service.RoleService;
import com.example.logindemo2.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
/**
 * @implNote
 * UserPrincipal class which implements UserDetails interface.
 * This way you get more flexibility and control over user authorization and authentication process.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(userName);
        log.debug(userName);
        if (user == null) {
            log.warn("Invalid username or password {}", userName);
            throw new UsernameNotFoundException("Invalid username or password.");
        }
/* return new org.springframework.security.core.userdetails.User(user.getUserName(),
user.getPassword(), mapRolesToAuthorities(user.getRoles()));*/
        return new CustomUserDetails(
                user.getEmail(),
                user.getPassword(),
                authorities(),
                user.getFullName(),
                user.getPhone(),
                user.getId()
                );
//        return new UserPrincipal(user, roleService.getRolesByUser(user.getId()));
    }

    public Collection<? extends GrantedAuthority> authorities (){
        return Arrays.asList(new SimpleGrantedAuthority("Email"));
    }

//    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
//        return roles.stream().map(role -> new
//                SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//    }
    /** Using model mapper helps to avoid extra coding
     * @param userDTO
     */
    @Transactional
    public void creat(UserDTO userDTO)
    {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(encoder.encode(user.getPassword()));
//        user.setRoles(Arrays.asList(roleService.findRoleByRoleName("ROLE_USER")));
        userRepository.save(user);
    }
    /** * In this example login and email has the same values @param email @return
     */

    @Override
    public User findUserByEmail(String email)
    {
        return userRepository.findUserByEmail(email);
    }
    @Override
    public User findUserByName(String fullName)
    {
        return userRepository.findUserByFullName(fullName);
    }
    @Override
    public User findUserById(Long id) { return  userRepository.getReferenceById(id);}
}
