package com.crud.invoices.respository;


import com.crud.invoices.domain.AppUser;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends CrudRepository<AppUser, Long> {

   Optional<AppUser> findByUsername(String username);

   Optional<AppUser> findAppUserByUsernameIgnoreCase(String username);

   Optional<AppUser> findByEmail(String email);

   @Override
   List<AppUser> findAll();

   @Override
   Optional<AppUser> findById(Long id);


   @Override
   void deleteById(Long id);

   @Override
   AppUser save(AppUser user);





}
