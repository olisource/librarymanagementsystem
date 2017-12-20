# librarymanagementsystem


Implement using REST, Springboot, DB (mysql/h2..) a simple config parameters management system for a Library management system:
  - Config parameters values can have different types (String, Int, Double, Boolean).
  - Config parameters are identified by a name, category and have a value.
  - Config parameters can be defined at 2 levels: 
    - Global (Library): this is the value for the entire system
    - Local (Branch): this is the overriding value for a specific sub-system (there can be multiple sub-systems)


The endpoints should allow to:
  - list parameters: (global/local levels). The query can be for global or local (explicit parameter ex: subSystem=1234) config. The result should indicate which parameters are local (overrides) vs global. 
  - create a parameter (global/local levels)
  - update a parameter (global/local levels)
  - delete a parameter (global/local levels)


Examples of parameters:
  1) Category: General, Name: BusinessName, Value: "Oli's Library"
  2) Category: Rental, Name: MaxRentalDuration, Value: 14 (days)
  3) Category: Customer, Name: CardRequired, Value: true 


For example 2) could be overwritten at local (branch) level: Category: Rental, Name: MaxRentalDuration, Value: 21 (days)
