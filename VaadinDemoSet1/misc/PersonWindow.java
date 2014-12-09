
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.Date;
import javax.swing.text.DateFormatter;


   public class PersonWindow extends Window {
     private TextField firstName;
     private TextField lastName;
     private TextField birthdate;
     Label id;
     public PersonWindow() {
       Person person = new Person(1L);
       person.setFirstName("John");
       person.setLastName("Doe");
       person.setBirthdate(new Date(0));
       BeanItem<Person> item = new BeanItem<Person>(person);
       id = new Label(item.getItemProperty("id"));
       firstName = new TextField("First name",
            item.getItemProperty("firstName"));
       lastName = new TextField("Last name",
            item.getItemProperty("lastName"));
       birthdate = new TextField("Birthdate", new
            DateFormatter(item.getItemProperty("birthdate")));
               final VerticalLayout layout = new VerticalLayout();
        

       layout.addComponent(id);
       layout.addComponent(firstName);
       layout.addComponent(lastName);
       layout.addComponent(birthdate);
} }
