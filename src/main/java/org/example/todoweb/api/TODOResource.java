package org.example.todoweb.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.todoweb.TODOItem;
import org.example.todoweb.TODOListRepository;

import java.util.List;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TODOResource
{

    private TODOListRepository repository = new TODOListRepository();

    @GET
    public List<TODOItem> getItems()
    {
        return repository.getItems();
    }

    @POST
    public Response addItem(TODOItem item) {
        if (item.getDescription() == null || item.getDescription().trim().isEmpty())
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("Description is required").build();
        }

        repository.addItem(item.getDescription());
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{index}")
    public Response deleteItem(@PathParam("index") int index)
    {
        try
        {
            repository.deleteItem(index);
            return Response.noContent().build();
        }
        catch (Exception e)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid index").build();
        }
    }

    @DELETE
    public Response clearAll()
    {
        repository.clearList();
        return Response.noContent().build();
    }
}
