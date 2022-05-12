package dev.applaudostudios.examples.assignmentweek5.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.applaudostudios.examples.assignmentweek5.common.dto.UserDTO;
import dev.applaudostudios.examples.assignmentweek5.persistence.UserRepository;
import dev.applaudostudios.examples.assignmentweek5.common.exception.CrudException;
import dev.applaudostudios.examples.assignmentweek5.persistence.model.User;
import dev.applaudostudios.examples.assignmentweek5.common.exception.EmailExistsException;

@Service
public class UserService implements CrudService<UserDTO, Long>{
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDTO createEntity(UserDTO entity) {
        if(getEntityByEmail(entity.getEmail()).isEmpty()){
            User user = new User(entity.getEmail(), entity.getFirstName(), entity.getLastName(), entity.getPhoneNumber());
            User savedUser = userRepository.save(user);
            entity.setId(savedUser.getId());
            return entity;
        } else {
            throw new EmailExistsException("The user already exists with the given email account.");
        }
    }

    @Override
    public UserDTO updateEntity(UserDTO entity){
        if(userRepository.existsById(entity.getId())){
            Optional<User> repoUser = userRepository.findById(entity.getId());
            if(repoUser.isPresent()){
                if(entity.getFirstName() != null ){
                    repoUser.get().setFirstName(entity.getFirstName());
                }
                if(entity.getLastName() != null){
                    repoUser.get().setLastName(entity.getFirstName());
                }
                if(entity.getPhoneNumber() != null){
                    repoUser.get().setPhoneNumber(entity.getPhoneNumber());
                }
                userRepository.save(repoUser.get());
                //entity.setEmail(repoUser.get().getEmail());
            }
            return entity;
        } else {
            throw new CrudException("User not found.");
        }
    }

    @Override
    public Optional<UserDTO> getEntity(Long key) {
        Optional<User> user = userRepository.findById(key);
        if(user.isPresent()){
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.get().getId());
            userDTO.setEmail(user.get().getEmail());
            userDTO.setFirstName(user.get().getFirstName());
            userDTO.setLastName(user.get().getLastName());
            userDTO.setPhoneNumber(user.get().getPhoneNumber());
            return Optional.of(userDTO);
        } else {
            throw new CrudException("User not found.");
        }
    }

     public Optional<UserDTO> getEntityByEmail(String email){
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if(user.isPresent()){
            UserDTO userDTO = new UserDTO();
            userDTO.setFirstName(user.get().getFirstName());
            userDTO.setLastName(user.get().getLastName());
            userDTO.setPhoneNumber(user.get().getPhoneNumber());
            return Optional.of(userDTO);
        } else {
            return Optional.empty();
        }
     }

    @Override
    public void deleteEntity(Long key) {
        //Not implemented yet
    }

}
