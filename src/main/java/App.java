import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.ArrayList;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("venues", Venue.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/band/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String inputName = request.queryParams("name");
      String inputGenre = request.queryParams("genre");
      Band newBand = new Band(inputName, inputGenre);
      newBand.save();
      response.redirect("/");
      return null;
    });

    post("/venue/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String inputName = request.queryParams("name");
      String inputNumber = request.queryParams("number");
      String inputCity = request.queryParams("city");
      String inputState = request.queryParams("state");
      Venue newVenue = new Venue(inputName, inputNumber, inputCity, inputState);
      newVenue.save();
      response.redirect("/");
      return null;
    });

    get("/band/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("band", Band.find(Integer.parseInt(request.params(":id"))));
      model.put("bands", Band.all());
      model.put("venues", Venue.all());
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venue/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("venue", Venue.find(Integer.parseInt(request.params(":id"))));
      model.put("template", "templates/venue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/band/:id/venue/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Integer idInt = Integer.parseInt(request.queryParams("idInput"));
      Integer idVenue = Integer.parseInt(request.queryParams("venue"));
      Band newBand = Band.find(idInt);
      Venue newVenue = Venue.find(idVenue);
      newBand.addVenue(newVenue);
      model.put("band", newBand);
      model.put("venues", Venue.all());
      response.redirect("/band/" + newBand.getId());
      return null;
    });

    post("/venue/:id/band/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Integer idInt = Integer.parseInt(request.queryParams("idInput"));
      Integer idBand = Integer.parseInt(request.queryParams("band"));
      Band newBand = Band.find(idBand);
      Venue newVenue = Venue.find(idInt);
      newVenue.addBand(newBand);
      model.put("venue", newVenue);
      response.redirect("/venue/" + newVenue.getId());
      return null;
    });

    get("/band/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band newBand = Band.find(Integer.parseInt(request.params(":id")));
      newBand.delete();
      response.redirect("/");
      return null;
    });

    get("/venue/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Venue newVenue = Venue.find(Integer.parseInt(request.params(":id")));
      newVenue.delete();
      response.redirect("/");
      return null;
    });

    post("/band/:id/update", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Integer idInt = Integer.parseInt(request.queryParams("idInput"));
      Band newBand = Band.find(idInt);
      String inputName = request.queryParams("name");
      String inputGenre = request.queryParams("genre");
      newBand.updateBand(inputName, inputGenre);
      response.redirect("/");
      return null;
    });
  }
}
