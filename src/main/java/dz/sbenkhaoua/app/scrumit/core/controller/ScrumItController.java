package dz.sbenkhaoua.app.scrumit.core.controller;


import dz.sbenkhaoua.app.scrumit.core.dto.FilterDTO;
import dz.sbenkhaoua.app.scrumit.core.dto.GenericDTO;
import dz.sbenkhaoua.app.scrumit.core.persistence.ScrumItEntity;
import dz.sbenkhaoua.app.scrumit.core.repository.ScrumItRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class ScrumItController<E extends ScrumItEntity> implements
        Serializable {

    private static final long serialVersionUID = 1L;

    public abstract ScrumItRepository<E> getSeaRepository();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String save(E entity) {
        try {
            return "success:" + getSeaRepository().save(entity);
        } catch (Exception e) {
            System.out.println(e.toString());
            return "failed:-1";
        }
    }

    @POST
    @Path("l")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> saveAll(List<E> entities) {
        List<String> list = new ArrayList<String>();
        try {
            list.add("success:");
            list.addAll(getSeaRepository().saveAll(entities));
            return list;
        } catch (Exception e) {
            list = null;
            list = new ArrayList<String>();
            list.add("failed:-1");
            return list;
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String update(E entity) {
        try {
            return "success:" + getSeaRepository().update(entity);
        } catch (Exception e) {
            return "failed:-1";
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String remove(@PathParam("id") String id) {
        boolean result = true;
        result = getSeaRepository().delete(id);
        if (result) {
            return "true";
        } else {
            return "false";
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public E find(@PathParam("id") String id) {
        return getSeaRepository().find(id);
    }

    @GET
    @Path("l")
    @Produces(MediaType.APPLICATION_JSON)
    public List<E> find() {
        return getSeaRepository().find();
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String count() {
        return String.valueOf(getSeaRepository().count());
    }

    @GET
    @Path("findRange/{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<E> findRange(@PathParam("from") Integer from,
                             @PathParam("to") Integer to) {
        return getSeaRepository().findRange(new int[]{from, to});
    }

    @GET
    @Path("findRange/{from}/{to}/{order}/{column}")
    @Produces(MediaType.APPLICATION_JSON)
    public GenericDTO<E> findRangeByOrder(@PathParam("from") Integer from,
                                          @PathParam("to") Integer to, @PathParam("order") String order,
                                          @PathParam("column") String column) {

        GenericDTO<E> list = new GenericDTO<E>();
        List<E> l = getSeaRepository().findRangeByOrder(new int[]{from, to},
                order, column);
        list.setList(l);
        list.setTotal(l.size());

        return list;
    }

    @POST
    @Path("filter")
    @Produces(MediaType.APPLICATION_JSON)
    public GenericDTO<E> filterByForm(FilterDTO filter) {

        if (filter.getCount() == null) {
            filter.setCount(10);
        }

        if (filter.getCount() == 0) {
            filter.setCount(10);
        }

        GenericDTO<E> list = new GenericDTO<E>();

        List<E> l = getSeaRepository().findRangeByOrderAndFilter(
                new int[]{(filter.getPage() - 1) * filter.getCount(),
                        filter.getPage() * filter.getCount()},
                filter.getInput(), filter.getOrder(), filter.getFilters(),
                filter.getId());

        list.setList(l);
        list.setTotal(l.size());

        return list;
    }


}
