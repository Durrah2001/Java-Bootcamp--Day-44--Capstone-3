
  *Models: Owner - Renting - Motorcycle - MaintenanceRequest - Course.
  -------------------------------------------------------------------------
 *Service:  OwnerService/ All CRUD.

 *Service:  MotorcycleService/  All CRUD + public List<Motorcycle> filterByPrice(Double minPrice, Double maxPrice). 

*Service: MaintenanceRequestService/ All CRUD except:  public List<MaintenanceRequestHistoryDTO> getMaintenanceHistory().

*Service: CourseService/ All CRUD +  public String awardBadgeToUser(Integer userId, Integer courseId) +  public String giftCourseToUser(Integer giverId, Integer receiverId, Integer courseId) +  public List<Course> getPopularCourses().

*Service: RentingService/ All CRUD.

*Service: EmailService/ All service.

 -------------------------------------------------------------------------
*ControllerAdvice / All controller.
 -------------------------------------------------------------------------

 *Controller: RentingController/ All CRUD.

 *Controller: OwnerController/ All CRUD.

 *Controller: MotorcycleController/ All CRUD +  public ResponseEntity filterMotorcyclesByPrice(
            @PathVariable Double minPrice,
            @PathVariable Double maxPrice).

   *Controller: MaintenanceRequestController/  All CRUD except: public ResponseEntity getMaintenanceHistory().

   *Controller: CourseController/  All CRUD + public ResponseEntity<String> awardBadgeToUser(@PathVariable Integer userId, @PathVariable Integer courseId) +  public ResponseEntity<String> giftCourseToUser(@RequestParam Integer giverId,
                                                   @RequestParam Integer receiverId,
                                                   @RequestParam Integer courseId) +   public ResponseEntity getPopularCourses().
  -------------------------------------------------------------------------
*Repository : RentingRepository/  Renting findRentingById(Integer id).

*Repository : OwnerRepository/   Owner findOwnerById(Integer id).

*Repository : MotorcycleRepository/  Motorcycle findMotorcycleById(Integer id) +  List<Motorcycle> findByPriceBetween(Double minPrice, Double maxPrice) + List<Motorcycle> findByPriceGreaterThanEqual(Double minPrice) +  List<Motorcycle> findByPriceLessThanEqual(Double maxPrice).

*Repository : MaintenanceRequestRepository/ MaintenanceRequest findMaintenanceRequestById(Integer id) + List<MaintenanceRequest> findByOwner(Owner owner) +  @Query("SELECT m FROM MaintenanceRequest m WHERE m.expert_name = :expertName AND m.pickupDate > :today And m.status = 'Pending'")
    List<MaintenanceRequest> findUpcomingRequestsByExpert(String expertName,  LocalDate today).

*Repository : MaintenanceExpertRepository/  MaintenanceExpert findMaintenanceExpertByName(String name).

*Repository : CourseRepository/ Course findCourseById(Integer id) + 
    @Query("SELECT c FROM Course c ORDER BY SIZE(c.bookings) DESC")
    List<Course> findTopCoursesByBookingCount().

 *Repository : BookingCourseRepository/     @Query("SELECT b FROM BookingCourse b WHERE b.user.id = :userId AND b.course.id = :courseId")
    List<BookingCourse> findByUserIdAndCourseId(Integer userId, Integer courseId).
  -------------------------------------------------------------------------

*DTO Out: RentingOutDTO.
*DTO Out: OwnerOutDTO.
*DTO Out: MotorcycleOutDTO.
*DTO Out: MaintenanceRequestOutDTO.
*DTO Out: CourseOutDTO.
  -------------------------------------------------------------------------
*DTO In: MaintenanceRequestDTO_In. 



    

    


    



                                                   


