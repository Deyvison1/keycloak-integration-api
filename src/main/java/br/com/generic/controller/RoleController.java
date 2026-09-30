package br.com.generic.controller;

import br.com.generic.constants.ApiConstants;
import br.com.generic.dto.response.RoleResponse;
import br.com.generic.service.RoleServiceImpl;
import br.com.sharedgeneric.dto.ApiResponse;
import br.com.sharedgeneric.dto.ApiResponseDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;

@Path(ApiConstants.API_V1 + "/roles")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class RoleController extends BaseController {

    private final RoleServiceImpl roleService;

    public RoleController(RoleServiceImpl roleService, final UriInfo uriInfo) {
        super(uriInfo);
        this.roleService = roleService;
    }

    @GET
    @RolesAllowed({"USER_MANAGE"})
    public ApiResponseDTO<List<RoleResponse>> findAll() {
        List<RoleResponse> findAll = roleService.findAll();
        return ApiResponse.success(
                ApiConstants.SUCCESS,
                findAll,
                getUriInfo().getRequestUri().getPath());
    }
}