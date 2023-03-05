package it.halb.roboapp.dataLayer.remoteDataSource.converters;

import java.util.function.Function;

import it.halb.roboapp.dataLayer.localDataSource.Account;
import it.halb.roboapp.dataLayer.remoteDataSource.scheme.model.UserResponse;

public class AccountConverter extends Converter<UserResponse, Account> {

    public AccountConverter() {
        super(AccountConverter::convertToEntity, AccountConverter::convertToDto);
    }

    private static UserResponse convertToDto(Account account){
        UserResponse response = new UserResponse();
        response.setUsername(account.getUsername());
        response.setAdmin(account.isRaceOfficer());
        response.setSuperAdmin(account.isAdmin());
        return response;
    }

    private static Account convertToEntity(UserResponse userResponse){
        return new Account(
                userResponse.getUsername(),
                null,
                Boolean.TRUE.equals(userResponse.getSuperAdmin()),
                Boolean.TRUE.equals(userResponse.getAdmin())
        );
    }

}
