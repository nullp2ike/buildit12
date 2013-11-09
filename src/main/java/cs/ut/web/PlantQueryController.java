package cs.ut.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cs.ut.domain.bean.PlantDTO;

@RequestMapping("/planthirerequests/queryPlant/**")
@Controller
public class PlantQueryController {

    @RequestMapping(method = RequestMethod.POST, value = "{id}")
    public void post(@PathVariable Long id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    }

    /*
    @RequestMapping
    public String index() {
        return "planthirerequests/queryPlant/index";
    }
    */
    @RequestMapping(method = RequestMethod.GET)
    public String searchPlants(ModelMap modelMap){
    	modelMap.put("plantquery", new PlantDTO());
    	return "planthirerequests/queryPlant/search";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String getPlants(ModelMap modelMap){
    	modelMap.put("plantquery", new PlantDTO());
    	return "planthirerequests/queryPlant/results";
    }
    
}
