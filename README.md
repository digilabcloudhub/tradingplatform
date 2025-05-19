#Information
This application has 3 functionality 
- Add Order
- Cancel Order
- Partial Fullfillment


#Layout
Below are the main components of Trading platform application
- 'PlatformController'
- 'PlatfromServiceIml'
- 'OrderDao'
- 'TradeDao'

All the other components act as helper to implement trading functionality

- 'AddOrderWorkflow' - Add Order Workflow validate and call related method to persist order
- 'CancelWorkflow' -  Cancel Workflow validate and call related method to delete order
- 'PartialFullfillmentWorkflow' - Partial Fullfillment validate and call related method for partial order
- 'TradingWorkflowHelper'- Common methods for all workflow

- 'CancelOrderValidator' - Custom method to validate cancel order request

#Local Setup
In order to run it locally , please setup lombok on local IDE




