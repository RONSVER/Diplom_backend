CREATE TABLE Users (
   UserID SERIAL PRIMARY KEY,
   Name VARCHAR NOT NULL,
   Email VARCHAR UNIQUE NOT NULL,
   PhoneNumber VARCHAR NOT NULL,
   PasswordHash VARCHAR NOT NULL,
   Role VARCHAR CHECK (Role IN ('Client', 'Administrator')) NOT NULL
);

CREATE TABLE Categories (
    CategoryID SERIAL PRIMARY KEY,
    Name VARCHAR NOT NULL
);

CREATE TABLE Products (
    ProductID SERIAL PRIMARY KEY,
    Name VARCHAR NOT NULL,
    Description TEXT,
    Price DECIMAL NOT NULL,
    CategoryID INT REFERENCES Categories(CategoryID) NOT NULL,
    ImageURL VARCHAR NOT NULL,
    DiscountPrice DECIMAL,
    CreatedAt TIMESTAMP NOT NULL,
    UpdatedAt TIMESTAMP NOT NULL
);

CREATE TABLE Cart (
    CartID SERIAL PRIMARY KEY,
    UserID INT REFERENCES Users(UserID) NOT NULL
);

CREATE TABLE CartItems (
    CartItemID SERIAL PRIMARY KEY,
    CartID INT REFERENCES Cart(CartID) NOT NULL,
    ProductID INT REFERENCES Products(ProductID) NOT NULL,
    Quantity INT NOT NULL
);

CREATE TABLE Orders (
    OrderID SERIAL PRIMARY KEY,
    UserID INT REFERENCES Users(UserID) NOT NULL,
    CreatedAt TIMESTAMP NOT NULL,
    DeliveryAddress VARCHAR NOT NULL,
    ContactPhone VARCHAR NOT NULL,
    DeliveryMethod VARCHAR NOT NULL,
    Status VARCHAR CHECK (Status IN ('New', 'Processing', 'Shipped', 'Delivered', 'Cancelled')) NOT NULL,
    UpdatedAt TIMESTAMP NOT NULL
);

CREATE TABLE OrderItems (
    OrderItemID SERIAL PRIMARY KEY,
    OrderID INT REFERENCES Orders(OrderID) NOT NULL,
    ProductID INT REFERENCES Products(ProductID) NOT NULL,
    Quantity INT NOT NULL,
    PriceAtPurchase DECIMAL NOT NULL
);

CREATE TABLE Favorites (
   FavoriteID SERIAL PRIMARY KEY,
   UserID INT REFERENCES Users(UserID) NOT NULL,
   ProductID INT REFERENCES Products(ProductID) NOT NULL
);