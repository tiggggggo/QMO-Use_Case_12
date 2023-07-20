import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.softserve.taf.models.enums.HttpStatus;
import com.softserve.taf.models.placeholder.user.UserDto;
import com.softserve.taf.services.common.AbstractWebEndpoint;

/**
 * A class that represents a User endpoint extending the AbstractWebEndpoint class.
 * Provides functionality to perform operations such as creating, updating, getting users.
 */
public class UserEndpoint extends AbstractWebEndpoint {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String USERS_END = "/users";
    private static final String USERS_RESOURCE_END = "/users/{userID}";

    /**
     * Constructor for creating a new UserEndpoint.
     *
     * @param specification The request specification to use.
     */
    public UserEndpoint(RequestSpecification specification) {
        super(specification);
    }

    /**
     * Creates a new user with the specified UserDto and returns the created UserDto.
     *
     * @param userDto The UserDto to create.
     * @return The created UserDto.
     */
    public UserDto create(UserDto userDto) {
        return create(userDto, HttpStatus.CREATED)
                .extract().as(UserDto.class);
    }

    /**
     * Creates a new user with the specified UserDto and status, returns a ValidatableResponse.
     *
     * @param userDto The UserDto to create.
     * @param status  The HttpStatus of the response to validate.
     * @return A ValidatableResponse of the create operation.
     */
    public ValidatableResponse create(UserDto userDto, HttpStatus status) {
        LOGGER.info("Create new User");
        return post(
                this.specification,
                USERS_END,
                userDto)
                .statusCode(status.getCode());
    }

    /**
     * Updates the user with the specified id and UserDto and returns the updated UserDto.
     *
     * @param id      The id of the user to update.
     * @param userDto The UserDto to update.
     * @return The updated UserDto.
     */
    public UserDto update(int id, UserDto userDto) {
        return update(userDto, id, HttpStatus.OK)
                .extract().as(UserDto.class);
    }

    /**
     * Updates the user with the specified id, UserDto, and status, returns a ValidatableResponse.
     *
     * @param userDto The UserDto to update.
     * @param id      The id of the user to update.
     * @param status  The HttpStatus of the response to validate.
     * @return A ValidatableResponse of the update operation.
     */
    public ValidatableResponse update(UserDto userDto, int id, HttpStatus status) {
        LOGGER.info("Update User by id [{}]", id);
        return put(
                this.specification,
                USERS_RESOURCE_END,
                userDto,
                id)
                .statusCode(status.getCode());
    }

    /**
     * Retrieves the user with the specified id and returns the UserDto.
     *
     * @param id The id of the user to retrieve.
     * @return The UserDto of the retrieved user.
     */
    public UserDto getById(String id) {
        return getById(id, HttpStatus.OK)
                .extract().as(UserDto.class);
    }

    /**
     * Retrieves the user with the specified id and status, returns a ValidatableResponse.
     *
     * @param id     The id of the user to retrieve.
     * @param status The HttpStatus of the response to validate.
     * @return A ValidatableResponse of the retrieval operation.
     */
    public ValidatableResponse getById(String id, HttpStatus status) {
        LOGGER.info("Get User by id [{}]", id);
        return get(
                this.specification,
                USERS_RESOURCE_END,
                id)
                .statusCode(status.getCode());
    }

    /**
     * Retrieves all the users and returns a list of UserDtos.
     *
     * @return A List of UserDtos representing all users.
     */
    public List<UserDto> getAll() {
        return List.of(getAll(HttpStatus.OK).extract().as(UserDto[].class));
    }

    /**
     * Retrieves all the users with the specified status, returns a ValidatableResponse.
     *
     * @param status The HttpStatus of the response to validate.
     * @return A ValidatableResponse of the retrieval operation.
     */
    public ValidatableResponse getAll(HttpStatus status) {
        LOGGER.info("Get all Users");
        ValidatableResponse response = get(this.specification, USERS_END);
        response.statusCode(status.getCode());
        return response;
    }

}
