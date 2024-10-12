package fa24.swp391.se1802.group3.capybook.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryExceptionResponseDTO {
    private int status;
    private String message;
    private long timeStamp;
}
