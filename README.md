![frame_firefox_mac_dark (1)](https://github.com/ManishCP/CarRentalSystem/blob/main/Screenshot%202024-01-31%20165302.png)
<h1 align="center">Car Rental Website Project</h1>  

The Car Rental System is a sophisticated platform tailored for car owners and customers, offering a seamless experience
in renting and managing fleets. Developed using Spring Boot, Hibernate, and MySQL this enterprise-level Java EE application 
follows an MVC design pattern to ensure scalability and maintainability.

For car owners, the system provides a comprehensive solution to rent out their vehicles efficiently. Owners can effortlessly 
monitor their fleet, tracking bookings, locations, and schedules. The platform empowers them to view detailed information about
each booking, allowing them to discern who has rented their cars, when, and where. Furthermore, owners have the flexibility to
allocate cars to different locations, ensuring optimal utilization of their fleet.

The customer-oriented features of the Car Rental System are designed to simplify the booking process. Customers can easily navigate 
through various available cars based on location, availability, and type. The system prioritizes user convenience, enabling
customers to book and reserve multiple cars seamlessly. Registered users enjoy personalized features such as booking history, 
facilitating a user-friendly and tailored experience.

The implementation of scalable Data Access Objects (DAO) with Hibernate ensures robust interactions with the database, 
enhancing data integrity and system reliability. User authentication is fortified through Spring Security, providing a 
secure environment for both car owners and customers. The reservation management and fleet tracking functionalities 
underscore the system's commitment to efficiency and transparency.

In terms of presentation, the use of Thymeleaf has resulted in the creation of reusable and aesthetically pleasing templates. 
This not only enhances the overall user interface but also contributes to the user-friendliness of the platform.

The Car Rental System stands as a testament to technological innovation in the domain of vehicle rental, offering a dynamic
and feature-rich solutions for both car owners and customers alike.

## Table of Contents

1. [Installation](#installation)
2. [Usage](#usage)
3. [Database Configuration](#database-configuration)
4. [Notable Design points](#notable-design-points)
5. [Database Schema](#database-schema)

## Installation

Software Installaion Prerequisites:

1. Java Development Kit (JDK)
2. Spring Tool Suite (STS)
3. Build Tool - Maven

Steps to run the project:

1. Clone the repository:

	```bash
	git clone https://github.com/ManishCP/CarRentalSystem.git
	 ```
2. Import the Project:

	1. Open STS.
	2. Go to File > Import....
	3. Choose Existing Maven Projects
	4. Navigate to the location where you saved your Spring Boot project and select the project directory.
	5. Click Finish to import the project.

3. Build the Project:

	Once the project is imported, STS will automatically start building it. You can check the progress in the console at the bottom.

4. Configure the Application Properties:

	Open the application.properties and configure database connection details.
 
