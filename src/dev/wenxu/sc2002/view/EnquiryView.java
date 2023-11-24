package dev.wenxu.sc2002.view;

import dev.wenxu.sc2002.entity.Camp;
import dev.wenxu.sc2002.entity.Enquiry;
import dev.wenxu.sc2002.entity.User;

import java.util.Scanner;

/**
 * A screen to view the user's submitted enquiry.
 */
public class EnquiryView extends View {
    /**
     * The camp that is being enquired.
     */
    private final Camp camp;
    /**
     * The user that is viewing/editing the camp.
     */
    private final User user;
    /**
     * The view to return to after EnquiryView exits.
     */
    private final View originalView;

    /**
     * Create a new EnquiryView.
     * @param camp The camp to create an enquiry for
     * @param user The user that is currently authenticated
     * @param originalView The view to return to
     */
    public EnquiryView(Camp camp, User user, View originalView) {
        this.camp = camp;
        this.user = user;
        this.originalView = originalView;
    }

    /**
     * Create an enquiry if the user does not have one and provide a menu to edit/view the enquiries.
     * @param sc The scanner that is scanning for inputs from stdin.
     * @return The view to return to
     */
    @Override
    public View display(Scanner sc) {
        Enquiry enquiry = camp.findEnquiry(user.getUserID()).orElseGet(() -> newEnquiry(sc));
        while (true) {
            clearScreen();
            System.out.printf("Your question: %s\n", enquiry.getQuestion());
            if (enquiry.getAnswer() != null) {
                System.out.printf("Answer: %s -%s\n", enquiry.getAnswer(), enquiry.getAnswererID());
            } else {
                System.out.println("Pending answer from staff or committee members.");
            }
            System.out.println();
            if (enquiry.getAnswer() == null) {
                System.out.println("(E)dit your enquiry");
                System.out.println("(D)elete your enquiry");
            } else {
                System.out.println("(A)sk a new enquiry");
            }
            System.out.println("(Q)uit");
            System.out.println();
            System.out.print("Choose your action: ");
            String command = sc.nextLine();

            if (enquiry.getAnswer() == null) {
                if (command.equalsIgnoreCase("e")) {
                    System.out.print("Enter your new question: ");
                    enquiry.setQuestion(sc.nextLine());
                    continue;
                }
                if (command.equalsIgnoreCase("d")) {
                    camp.deleteEnquiry(enquiry);
                    return originalView;
                }
            } else if (command.equalsIgnoreCase("a")) {
                enquiry = newEnquiry(sc);
                continue;
            }
            if (command.equalsIgnoreCase("q")) {
                return originalView;
            }

            error = "Invalid choice. Please try again.";
        }
    }

    /**
     * Create a new enquiry based on user input and inserts it into camp.
     * @param sc The scanner reading from stdin
     * @return The enquiry created
     */
    private Enquiry newEnquiry(Scanner sc) {
        clearScreen();
        System.out.print("Enter your question: ");
        String question = sc.nextLine();
        Enquiry enquiry = new Enquiry(user.getUserID(), question);
        camp.addEnquiry(enquiry);
        return enquiry;
    }
}
