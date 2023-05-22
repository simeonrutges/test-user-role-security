package nl.novi.automate.controller;
import nl.novi.automate.dto.ReviewDto;
import nl.novi.automate.service.ReviewService;
import nl.novi.automate.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final UserService userService;
    private  final ReviewService reviewService;
    public ReviewController(UserService userService, ReviewService reviewService) {
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @GetMapping("")
    public List<ReviewDto> getAllReviews() {

        List<ReviewDto> dtos = reviewService.getAllReviews();

        return dtos;
    }

//    //////
//    @GetMapping("")
//    public ResponseEntity<List<ReviewDto>> getAllReviews(Principal principal,
//                                                         @RequestParam(value = "reviewedUser", required = false) Optional<User> reviewedUser) {
//
//        List<ReviewDto> dtos;
//
//        if (reviewedUser.isEmpty()){
//            dtos = reviewService.getAllReviews();
//        } else {
//            UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
//            dtos = reviewService.getAllReviewsByReviewedUser((Principal) userDetails);
//        }
//
//        return ResponseEntity.ok().body(dtos);
//    }


    //////


    @GetMapping("/{id}")
    public ReviewDto getReview(@PathVariable("id") Long id) {

        ReviewDto dto = reviewService.getReview(id);

        return dto;
    }

@PostMapping("")
public ResponseEntity<Object> createReview(@Valid @RequestBody ReviewDto reviewDto, BindingResult br) {

    if (br.hasErrors()) {
        StringBuilder sb = new StringBuilder();
        for (FieldError fe : br.getFieldErrors()) {
            sb.append(fe.getField() + ": ");
            sb.append(fe.getDefaultMessage());
            sb.append("\n");
        }
        return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
    } else {

        ReviewDto savedReview = reviewService.createReview(reviewDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedReview.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedReview);
    }
}


    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable("id") Long id) {
        reviewService.deleteReview(id);
    }

    @PutMapping("/{id}")
    public ReviewDto updateReview(@PathVariable("id") Long id, @RequestBody ReviewDto dto) {
        reviewService.updateReview(id, dto);
        return dto;
    }
    // alles werkt behalve update

    //    @PostMapping("")
//    public ReviewDto addReview(@RequestBody ReviewDto dto) {
//        ReviewDto dto1 = reviewService.addReview(dto);
//        return dto1;
//    }
//    @PostMapping("/reviews")
//    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) {
//        ReviewDto createdReview = ReviewService.createReview(reviewDto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
//    }
}
