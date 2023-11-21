package io.bootify.infinity_cart.service;

import io.bootify.infinity_cart.config.PasswordEncoder;
import io.bootify.infinity_cart.domain.User;
import io.bootify.infinity_cart.model.UserDTO;
import io.bootify.infinity_cart.repos.UserRepository;
import io.bootify.infinity_cart.util.NotFoundException;
import java.util.List;

import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        userDTO.setGender(user.getGender());
        userDTO.setPhone(user.getPhone());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setPassword(PasswordEncoder.getPasswordEncoder().encode(userDTO.getPassword()));
        user.setRole("USER");
        user.setGender(userDTO.getGender());
        user.setPhone(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());
        return user;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if(user==null){
            throw new NotFoundException("User Not Found");
        }

        return user;
    }

    public UserDTO getAccount(String username) {
        final User user = userRepository.findByUsername(username);
        return mapToDTO(user,new UserDTO());
    }

    public boolean userExists(final String username){
        return userRepository.existsByUsernameIgnoreCase(username);
    }
}
