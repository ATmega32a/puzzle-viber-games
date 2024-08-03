package ru.gamechatbot.puzzlevibergames.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gamechatbot.puzzlevibergames.model.User;
import ru.gamechatbot.puzzlevibergames.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    User user;



    public boolean addUser(User user) {
        User userFromDb = userRepository.findByUserName(user.getUserName());
        if (userFromDb != null) {
            return false;
        }
        userRepository.save(user);
        return true;
    }


    public void saveUser(String name, String viberId, String avatar) {
        if (userRepository.findByUserId(viberId).isEmpty()){
            user.setUserName(name);
            user.setUserId(viberId);
            user.setAvatar(avatar);
            userRepository.save(user);
        }

    }

//    public void subscribe(User currentUser, User user) {
//        user.getSubscribers().add(currentUser);
//        userRepository.save(user);
//    }
//
//    public void unsubscribe(User currentUser, User user) {
//        user.getSubscribers().remove(currentUser);
//        userRepository.save(user);
//    }
}
