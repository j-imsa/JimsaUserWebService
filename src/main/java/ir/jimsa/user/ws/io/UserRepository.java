package ir.jimsa.user.ws.io;

import ir.jimsa.user.ws.io.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserEntityByEmail(String email);

    UserEntity findUserEntityByUserId(String userId);
}
