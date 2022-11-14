package ir.jimsa.user.ws.ui.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserRest {
    public String name;
    public String family;
    public String email;
    public String password;
}
