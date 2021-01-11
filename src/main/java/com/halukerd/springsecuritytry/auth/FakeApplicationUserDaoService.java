package com.halukerd.springsecuritytry.auth;

import com.google.common.collect.Lists;
import com.halukerd.springsecuritytry.security.PasswordConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.halukerd.springsecuritytry.security.ApplicationUserRole.*;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        return Lists.newArrayList(
                new ApplicationUser(
                        HEADMASTER.getGrantedAuthorities(),
                        "albus",
                        passwordEncoder.encode( "sherbetlemon"),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        PREFECT.getGrantedAuthorities(),
                        "percy",
                        passwordEncoder.encode( "iamprefect"),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        STUDENT.getGrantedAuthorities(),
                        "neville",
                        passwordEncoder.encode( "1234"),
                        true,
                        true,
                        true,
                        true
                )
        );
    }
}
