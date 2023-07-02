package com.crud.invoices.respository;


import com.crud.invoices.domain.AppUser;
import com.crud.invoices.domain.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);

    Optional<ConfirmationToken> findByAppUser(AppUser appUser);
    @Override
    void deleteById(Long id);

    @Override
    List<ConfirmationToken> findAll();
}
