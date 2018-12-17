package ex.wicket;

import ex.entity.User;
import ex.service.UserService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Arrays;
import java.util.List;

public class UserPage extends WebPage {

    @SpringBean
    private UserService userService;

    private List<User> users = userService.findUsers();
    private String selected = "First name";
    private ListView<User> userListView;


    public UserPage() {
        userListView = new ListView<User>("users", users) {
            @Override
            protected void populateItem(ListItem<User> listItem) {
                listItem.add(new Label("id", new PropertyModel(listItem.getModel(), "id")));
                listItem.add(new Label("name", new PropertyModel(listItem.getModel(), "name")));
                listItem.add(new Label("lastName", new PropertyModel(listItem.getModel(), "lastName")));
            }
        };
        userListView.setOutputMarkupId(true);
        add(userListView);

        final WebMarkupContainer listContainer = new WebMarkupContainer("container");
        listContainer.add(userListView);
        listContainer.setOutputMarkupId(true);
        add(listContainer);

        Form form = new Form("userForm");
        final TextField<String> search = new TextField<>("search", Model.of(""));
        List<String> searchType = Arrays.asList("First name", "Last name");
        RadioChoice<String> radioGroup = new RadioChoice<>("radioGroup", new PropertyModel<String>(this, "selected"), searchType);

        AjaxSubmitLink link = new AjaxSubmitLink("searchLink") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                if ("First name".equals(selected)) {
                    users = userService.findUsersByName(search.getDefaultModelObjectAsString());
                } else {
                    users = userService.findUsersByLastName(search.getDefaultModelObjectAsString());
                }
                userListView.setDefaultModelObject(users);
                target.add(listContainer);
            }
        };
        AjaxLink all = new AjaxLink("linkAll") {    //Здесь высвечивает ошибку, однако программа "билдится" и работает нормально
            @Override
            public void onClick(AjaxRequestTarget target) {
                users = userService.findUsers();
                userListView.setDefaultModelObject(users);
                target.add(listContainer);
            }
        };

        form.add(search);
        form.add(radioGroup);
        form.add(link);
        add(all);
        add(form);
    }
}
