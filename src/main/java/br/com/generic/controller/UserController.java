package br.com.generic.controller;

import br.com.generic.constants.ApiConstants;
import br.com.generic.dto.request.UserCreateRequest;
import br.com.generic.dto.response.UserResponse;
import br.com.generic.dto.request.UserUpdateRequest;
import br.com.generic.service.UserServiceImpl;
import br.com.sharedgeneric.dto.ApiResponse;
import br.com.sharedgeneric.dto.ApiResponseDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;

@Path(ApiConstants.API_V1 + "/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController extends BaseController {

    private final UserServiceImpl userService;

    public UserController(
            UserServiceImpl userService,
            final UriInfo uriInfo
    ) {
        super(uriInfo);
        this.userService = userService;
    }

    @GET
    @RolesAllowed({"USER", "USER_MANAGE"})
    public ApiResponseDTO<List<UserResponse>> getAll() {

        List<UserResponse> users = userService.findAll();

            return ApiResponse.success(
                ApiConstants.SUCCESS,
                users,
                getUriInfo().getRequestUri().getPath()
        );
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("USER_MANAGE")
    public ApiResponseDTO<UserResponse> getById(
            @PathParam("id") String id
    ) {
        UserResponse user = userService.findById(id);

        return ApiResponse.success(
                ApiConstants.SUCCESS,
                user,
                getUriInfo().getRequestUri().getPath()
        );
    }

    @POST
    @RolesAllowed("USER_MANAGE")
    public ApiResponseDTO<UserResponse> insert(
            UserCreateRequest request
    ) {
        UserResponse user = userService.create(request);

        return ApiResponse.success(
                ApiConstants.SUCCESS,
                user,
                getUriInfo().getRequestUri().getPath()
        );
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("USER_MANAGE")
    public ApiResponseDTO<UserResponse> update(
            @PathParam("id") String id,
            UserUpdateRequest request
    ) {
        UserResponse user = userService.update(id, request);

        return ApiResponse.success(
                ApiConstants.SUCCESS,
                user,
                getUriInfo().getRequestUri().getPath()
        );
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("USER_MANAGE")
    public ApiResponseDTO<Void> delete(
            @PathParam("id") String id
    ) {
        userService.delete(id);

        return ApiResponse.success(
                ApiConstants.SUCCESS,
                null,
                getUriInfo().getRequestUri().getPath()
        );
    }
}
