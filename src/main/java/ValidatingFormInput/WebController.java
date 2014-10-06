package ValidatingFormInput;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Controller
public class WebController extends WebMvcConfigurerAdapter{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/results").setViewName("results");
	}

	// Get method return the form template and associate the form attribute with the parameter person object
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String showForm(Person person) {
		return "form";
	}
	
	// A person object marked up with @Valid to gather the attributes filled out in the form you’re about to build.
	// A bindingResult object so you can test for and retrieve validation errors.
	// This BindingResult is kind of data holder to hold the person info and also the validation result on the person object
	@RequestMapping(value="/", method=RequestMethod.POST)
	public String checkPersonInfo(@Valid Person person, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "form";
		}
		return "redirect:/results";
	}
}
