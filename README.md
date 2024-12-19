 public void updateMaintenanceRequestStatusToCompleted(Integer maintenanceRequest_id, String expertName) {
        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_id);

        if (maintenanceRequest == null)
            throw new ApiException("MaintenanceRequest not found!");

        // Check if the current expert is the one assigned to the request
        if (!maintenanceRequest.getExpert_name().equalsIgnoreCase(expertName)) {
            throw new ApiException("Only the expert can mark the maintenance request as completed!");
        }

        // Only allow the status to be updated if the request is in 'Pending' status
        if (!"Pending".equalsIgnoreCase(maintenanceRequest.getStatus())) {
            throw new ApiException("Maintenance request is not in a Pending status, it cannot be marked as completed!");
        }

        // Update status
        maintenanceRequest.setStatus("Completed");
        maintenanceRequestRepository.save(maintenanceRequest);
    }


//////////////////////////////

public Map<String, Object> generateMaintenanceRequestInvoice(Integer maintenanceRequest_id) {
        // Retrieve the maintenance request by ID
        MaintenanceRequest request = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_id);
        if (request == null) {
            throw new ApiException("Request not found!");
        }

        // Check if the request's status is "Completed"
        if (!"Completed".equalsIgnoreCase(request.getStatus())) {
            throw new ApiException("Cannot generate invoice for pending requests!"); // Only allow invoice generation for completed requests
        }

        Map<String, Object> invoiceData = new HashMap<>();
        invoiceData.put("Request Id", request.getId());
        invoiceData.put("Owner Name", request.getOwner().getName());
        invoiceData.put("Owner Email", request.getOwner().getEmail());
        invoiceData.put("Total Price", request.getTotalPrice());
        invoiceData.put("Date Generated", LocalDate.now());
        invoiceData.put("Request Status", "Completed");

        return invoiceData;
    }

//////////////////////////////////

  public void notifyOwnerOnCompletion(Integer maintenanceRequest_Id) {
        MaintenanceRequest request = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_Id);

        if (request == null) {
            throw new RuntimeException("Maintenance Request not found!");
        }

        if (!"Completed".equalsIgnoreCase(request.getStatus())) {
            throw new RuntimeException("Request is not completed yet!");
        }

        // Retrieve the owner's email
        Owner owner = request.getOwner();
        String ownerEmail = owner.getEmail();

        // build the email message
        String subject = "Your Maintenance Request is Completed!";
        String text = "Dear " + owner.getName() + ",\n\n" +
                "Your maintenance request for motorcycle: " + request.getMotorcycle_id() + " has been completed successfully and ready for pickup.\n\n" +
                "Thank you for using our services!";

        // Send the email notification
        emailService.sendEmail(ownerEmail, subject, text);
    }

///////////////////////////////////

 public List<MaintenanceRequest> getMaintenanceHistoryByOwner(Integer ownerId) {
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if(owner==null)
                throw  new ApiException("Owner not found!");

        return maintenanceRequestRepository.findByOwner(owner);
    }
/////////////////////////////////

  public void notifyExpert(Integer maintenanceId) {

        MaintenanceRequest request = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceId);
        if (request == null) {
            throw new ApiException("Maintenance request not found!");
        }

        if (!request.getStatus().equalsIgnoreCase("Pending")) {
            throw new ApiException("Can not send notification for completed requests!!");
        }

        MaintenanceExpert expert = maintenanceExpertRepository.findMaintenanceExpertByName(request.getExpert_name());
        if (expert == null) {
            throw new ApiException("Expert not found.");
        }


        String subject = "New Maintenance Request Assigned!";
        String text = String.format("Dear %s,\n\nYou have been assigned a new maintenance request for motorcycle ID: %d.\n\nPlease review the request.\n\nBest regards,\nMaintenance System",
                expert.getName(), request.getMotorcycle_id());

        // Sending the email to the expert
        emailService.sendEmail(expert.getEmail(), subject, text);

    }

//////////////////////////////////

  public List<MaintenanceRequest> getUpcomingRequestsByExpert(String expertName, LocalDate today) {
        return maintenanceRequestRepository.findUpcomingRequestsByExpert(expertName, today);
    }

//////////////////////////////////

    public List<Course> getPopularCourses() {

        // Fetch all courses with their associated bookings, ordered by booking count
        return courseRepository.findTopCoursesByBookingCount();
    }

///////////////////////////////

   public String giftCourseToUser(Integer giverId, Integer receiverId, Integer courseId) {

        // Fetch users and course
        User giver = userRepository.findUserById(giverId);
        if (giver == null)
            throw new ApiException("Giver not found!");

        User receiver = userRepository.findUserById(receiverId);
        if (receiver == null)
            throw new ApiException("Receiver not found!");

        Course course = courseRepository.findCourseById(courseId);
        if (course == null)
            throw new ApiException("Course not found!");

        // Check if the receiver has already been gifted this course
        for (BookingCourse booking : receiver.getBookings()) {
            if (booking.getCourse().getId().equals(courseId)) {
                throw new ApiException("Receiver already has this course!");
            }
        }

        // Create a new booking for the receiver
        BookingCourse newBooking = new BookingCourse();
        newBooking.setCourse(course);
        newBooking.setUser(receiver);  // Set receiver as the user
        newBooking.setCourseStartDate(LocalDate.now());
        newBooking.setCourseEndDate(LocalDate.now().plusDays(course.getDuration()));  // Assuming course duration in days

        // Save
        bookingCourseRepository.save(newBooking);

        // Send email to both giver and receiver
        sendGiftNotificationEmail(giver, receiver, course);

        return "Course gifted successfully!";
    }

    // Helper method
    private void sendGiftNotificationEmail(User giver, User receiver, Course course) {
        // Email to giver
        String giverEmailContent = "Dear " + giver.getName() + ",\n\n" +
                "You have successfully gifted the course \"" + course.getName() + "\" to " + receiver.getName() + ".\n" +
                "The receiver has been notified about the gift and will be able to start the course soon.\n\n" +
                "Thank you for your generosity!\n\n" +
                "Best regards.\n";

        // Email  receiver
        String receiverEmailContent = "Dear " + receiver.getName() + ",\n\n" +
                "You have received a wonderful gift! " + giver.getName() + " has gifted you the course \"" + course.getName() + "\".\n" +
                "You can attend the course as soon as possible!\n\n" +
                "Enjoy learning!\n\n" +
                "Best regards.\n" ;

        // Send email to the giver
        emailService.sendEmail(giver.getEmail(), "Course Gift Confirmation!", giverEmailContent);

        // Send email to the receiver
        emailService.sendEmail(receiver.getEmail(), "You've Received a Course Gift!", receiverEmailContent);
    }
////////////////////////////////////////

  public String awardBadgeToUser(Integer userId, Integer courseId) {

        // Fetch the booking record for the user and course
        List<BookingCourse> bookingCourses = bookingCourseRepository.findByUserIdAndCourseId(userId, courseId);

        if (bookingCourses.isEmpty()) {
            throw new ApiException("No booking found for this user and course!");
        }

        BookingCourse booking = bookingCourses.get(0);

        // Validate if course is completed (check if courseEndDate is in the past)
        if (booking.getCourseEndDate() == null || !LocalDate.now().isAfter(booking.getCourseEndDate())) {
            throw new ApiException("Course is not completed yet or courseEndDate is missing!");
        }

        // Check if the user already has the badge
        User user = booking.getUser();
        String badge = "***Completed Course*** : " + booking.getCourse().getName();

        // If badges list is null, initialize it
        if (user.getBadges() == null) {
            user.setBadges(new HashSet<>());
        }

        // If the badge is already present, return message
        if (user.getBadges().contains(badge)) {
            return "User already has this badge!";
        }

        // Award badge
        user.getBadges().add(badge);

        userRepository.save(user);

        return "Badge awarded successfully!";
    }

///////////////////////////////////////////

   public List<Motorcycle> filterByPrice(Double minPrice, Double maxPrice) {
        if (minPrice == null && maxPrice == null) {
            return motorcycleRepository.findAll(); // No filter, return all motorcycles
        }
        if (minPrice != null && maxPrice != null) {
            return motorcycleRepository.findByPriceBetween(minPrice, maxPrice);
        }
        if (minPrice != null) {
            return motorcycleRepository.findByPriceGreaterThanEqual(minPrice);
        }
        if (maxPrice != null) {
            return motorcycleRepository.findByPriceLessThanEqual(maxPrice);
        }
        return motorcycleRepository.findAll();
    }
