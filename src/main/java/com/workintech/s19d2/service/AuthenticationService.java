package com.workintech.s19d2.service;

import com.workintech.s19d2.entity.Member;
import com.workintech.s19d2.entity.Role;
import com.workintech.s19d2.repository.MemberRepository;
import com.workintech.s19d2.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(MemberRepository memberRepository,
                                 RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member register(String email, String password) {
        Optional<Member> existing = memberRepository.findByEmail(email);
        if (existing.isPresent()) {
            throw new RuntimeException("User with given email already exist");
        }

        // Encode the password
        String encodedPassword = passwordEncoder.encode(password);

        // Fetch the ADMIN role (as required by the tests)
        Role adminRole = roleRepository.findByAuthority("ADMIN")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(encodedPassword);
        member.setRoles(Collections.singletonList(adminRole));

        return memberRepository.save(member);
    }
}
