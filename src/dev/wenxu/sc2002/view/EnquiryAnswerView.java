package dev.wenxu.sc2002.view;

import dev.wenxu.sc2002.entity.Camp;
import dev.wenxu.sc2002.entity.Enquiry;
import dev.wenxu.sc2002.entity.User;

import java.util.Optional;
import java.util.Scanner;

public class EnquiryAnswerView extends View {
    /**
     * The camp that is being enquired.
     */
    private final Camp camp;
    /**
     * The user that is viewing/editing the camp.
     */
    private final User user;
    /**
     * The view to return to after EnquiryAnswerView exits.
     */
    private final View originalView;

    public EnquiryAnswerView(Camp camp, User user, View originalView) {
        this.camp = camp;
        this.user = user;
        this.originalView = originalView;
    }

    @Override
    public View display(Scanner sc) {
        while (true) {
            clearScreen();
            Optional<Enquiry> enquiry = camp.getEnquiries().
                    stream().
                    filter(e -> e.getAnswer() == null).
                    findFirst();
            if (enquiry.isEmpty()) {
                System.out.println("There are no unanswered enquiries.");
                System.out.println();
                System.out.println("(Q)uit");
                System.out.println();
                String command = sc.nextLine();
                if (command.equalsIgnoreCase("q")) {
                    return originalView;
                }
                continue;
            }
            System.out.printf("Question: %s\n", enquiry.get().getQuestion());
            System.out.printf("Asked by: %s\n", enquiry.get().getAskerID());
            System.out.println();
            System.out.println("Please enter your answer. Hit enter without answer to exit.");
            String answer = sc.nextLine();
            if (answer.isBlank()) {
                return originalView;
            }
            enquiry.get().setAnswer(user.getUserID(), answer);
        }
    }
}
