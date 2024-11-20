package fa24.swp391.se1802.group3.capybook.controllers;

import fa24.swp391.se1802.group3.capybook.daos.CartDAO;
import fa24.swp391.se1802.group3.capybook.models.CartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@CrossOrigin(origins = "http://localhost:5175")
public class CartController {

    private final CartDAO cartDAO;

    @Autowired
    public CartController(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBookToCart(@RequestParam String username,
                                                @RequestParam int bookID,
                                                @RequestParam int quantity) {
        try {
            cartDAO.addBookToCart(username, bookID, quantity);
            return ResponseEntity.status(HttpStatus.CREATED).body("Book added to cart successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CartDTO>> viewCart(@RequestParam String username) {
        try {
            List<CartDTO> cart = cartDAO.viewCart(username);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> editQuantity(@RequestParam String username,
                                               @RequestParam int bookID,
                                               @RequestParam int quantity) {
        try {
            cartDAO.editQuantity(username, bookID, quantity);
            return ResponseEntity.ok("Quantity updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBookFromCart(@RequestParam String username,
                                                     @RequestParam int bookID) {
        try {
            cartDAO.deleteBookFromCart(username, bookID);
            return ResponseEntity.ok("Book removed from cart successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}
