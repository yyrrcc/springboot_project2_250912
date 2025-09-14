package com.mycompany.p2.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
	// userid로 정보 조회
	public Optional<UserEntity> findByUserid(String userid);

}
