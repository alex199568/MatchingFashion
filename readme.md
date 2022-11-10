# Matching Fashion

## App Features
- Download the list of products from the api
- Display product name, image, designer, price in GBP
- Settings screen where user can select an additional currency
- If a currency is selected, the app displays the price in that currency next to the price in GBP
- Currency rates are downloaded from a 3rd party API
- Currency rates are cached in memory for 1 minute, each minute the rates are going to be redownloaded from the api
- Products are cached in memory for 5 minutes
- Before products are downloaded a progress indicator is shown.

## TODOs
- Cache responses in local database
- Handle possible backend errors
- Unit tests
- Fonts
- Theme
- Improve product item layout
- Different screen sizes and orientations
- Make all of the currencies from the rates API available to the user
