import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.softserve.taf.models.enums.HttpStatus;
import com.softserve.taf.models.placeholder.comment.CommentDto;
import com.softserve.taf.services.common.AbstractWebEndpoint;

/**
 * A class that represents a Comment endpoint extending the AbstractWebEndpoint class.
 * Provides functionality to perform operations such as creating, updating, and retrieving comments.
 */
public class CommentEndpoint extends AbstractWebEndpoint {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COMMENTS_END = "/comments";
    private static final String COMMENTS_RESOURCE_END = "/comments/{commentID}";

    /**
     * Constructor for creating a new CommentEndpoint.
     *
     * @param specification The request specification to use.
     */
    public CommentEndpoint(RequestSpecification specification) {
        super(specification);
    }

    /**
     * Creates a new comment with the specified CommentDto and returns the created CommentDto.
     *
     * @param commentDto The CommentDto to create.
     * @return The created CommentDto.
     */
    public CommentDto create(CommentDto commentDto) {
        return create(commentDto, HttpStatus.CREATED)
                .extract().as(CommentDto.class);
    }

    /**
     * Creates a new comment with the specified CommentDto and status, returns a ValidatableResponse.
     *
     * @param commentDto The CommentDto to create.
     * @param status The HttpStatus of the response to validate.
     * @return A ValidatableResponse of the create operation.
     */
    public ValidatableResponse create(CommentDto commentDto, HttpStatus status) {
        LOGGER.info("Create new Comment");
        return post(
                this.specification,
                COMMENTS_END,
                commentDto)
                .statusCode(status.getCode());
    }

    /**
     * Updates the comment with the specified id and CommentDto and returns the updated CommentDto.
     *
     * @param id The id of the comment to update.
     * @param commentDto The CommentDto to update.
     * @return The updated CommentDto.
     */
    public CommentDto update(int id, CommentDto commentDto) {
        return update(commentDto, id, HttpStatus.OK)
                .extract().as(CommentDto.class);
    }

    /**
     * Updates the comment with the specified id, CommentDto, and status, returns a ValidatableResponse.
     *
     * @param commentDto The CommentDto to update.
     * @param id The id of the comment to update.
     * @param status The HttpStatus of the response to validate.
     * @return A ValidatableResponse of the update operation.
     */
    public ValidatableResponse update(CommentDto commentDto, int id, HttpStatus status) {
        LOGGER.info("Update Comment by id [{}]", id);
        return put(
                this.specification,
                COMMENTS_RESOURCE_END,
                commentDto,
                id)
                .statusCode(status.getCode());
    }

    /**
     * Retrieves the comment with the specified id and returns the CommentDto.
     *
     * @param id The id of the comment to retrieve.
     * @return The CommentDto of the retrieved comment.
     */
    public CommentDto getById(int id) {
        return getById(id, HttpStatus.OK)
                .extract().as(CommentDto.class);
    }

    /**
     * Retrieves the comment with the specified id and status, returns a ValidatableResponse.
     *
     * @param id The id of the comment to retrieve.
     * @param status The HttpStatus of the response to validate.
     * @return A ValidatableResponse of the retrieval operation.
     */
    public ValidatableResponse getById(int id, HttpStatus status) {
        LOGGER.info("Get Comment by id [{}]", id);
        return get(
                this.specification,
                COMMENTS_RESOURCE_END,
                String.valueOf(id))
                .statusCode(status.getCode());
    }

    /**
     * Retrieves all the comments and returns a list of CommentDtos.
     *
     * @return A List of CommentDtos representing all comments.
     */
    public List<CommentDto> getAll() {
        return List.of(getAll(HttpStatus.OK).extract().as(CommentDto[].class));
    }

    /**
     * Retrieves all the comments with the specified status, returns a ValidatableResponse.
     *
     * @param status The HttpStatus of the response to validate.
     * @return A ValidatableResponse of the retrieval operation.
     */
    public ValidatableResponse getAll(HttpStatus status) {
        LOGGER.info("Get all Comments");
        ValidatableResponse response = get(this.specification, COMMENTS_END);
        response.statusCode(status.getCode());
        return response;
    }

}
