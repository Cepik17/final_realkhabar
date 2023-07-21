package kz.bitlab.realKhabar.realKhabar.mappers;

import kz.bitlab.realKhabar.realKhabar.dtos.UserView;
import kz.bitlab.realKhabar.realKhabar.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {


    UserView toView(User user);

    User toEntity(UserView userView);
}
