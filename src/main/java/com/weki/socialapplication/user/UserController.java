package com.weki.socialapplication.user;

import com.weki.socialapplication.exception.PageNotFoundException;
import com.weki.socialapplication.user.post.Post;
import com.weki.socialapplication.user.post.PostRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    private UserRepository userRepository;
    private final PostRepository postRepository;

    public UserController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping(path = "/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(path = "/users")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }


    @GetMapping(path = "/users/{id}")
    public User retrieveOneUser(@PathVariable Integer id) {
        User user = userRepository.findById(id).orElse(null);

        if(user == null) throw new PageNotFoundException("User Does Not Exist");

        return user;
    }


    @DeleteMapping(path = "/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        User savedUser = userRepository.findById(id).orElse(null);
        if(savedUser == null) throw new PageNotFoundException("User Does Not Exist");
        userRepository.deleteById(id);
    }

    @GetMapping(path = "/users/{id}/posts")
    public List<Post> retrieveAllPosts(@PathVariable Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) throw new PageNotFoundException("User Not Found");

        List<Post> posts = user.getPost();
        if(posts.isEmpty()) throw new PageNotFoundException("No Posts Available");

        return posts;
    }

    //posts/id get delete
    //posts post

    @GetMapping(path = "/users/{id}/posts/{post_id}")
    public Post retrieveOnePost(@PathVariable Integer id, @PathVariable Integer post_id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) throw new PageNotFoundException("User Not Found");

        Post post = postRepository.findById(post_id).orElse(null);
        if(post == null) throw new PageNotFoundException("No Posts Available");

        return post;
    }

    @DeleteMapping(path = "/users/{id}/posts/{post_id}")
    public void deleteOneUser(@PathVariable Integer id, @PathVariable Integer post_id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) throw new PageNotFoundException("User Not Found");

        Post post = postRepository.findById(post_id).orElse(null);
        if(post == null) throw new PageNotFoundException("No Posts Available");

        postRepository.deleteById(post_id);
    }

    @PostMapping(path = "/users/{id}/posts")
    public ResponseEntity<User> addUserPost(@PathVariable Integer id, @RequestBody Post post) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) throw new PageNotFoundException("User Not Found");

        post.setUser(user);
        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPost.getId()).toUri();
        return ResponseEntity.created(location).build();
    }


}
