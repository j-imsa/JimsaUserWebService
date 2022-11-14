package ir.jimsa.user.ws.ui.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OperationStatusModel {
    private String operationName;
    private String operationResult;
}
