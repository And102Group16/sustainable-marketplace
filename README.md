# Milestone 1 - Sustainify (Unit 7)

## Table of Contents

1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)

## Overview

### Description

Sustainify is a sustainable marketplace app. This is a marketplace where users can buy or sell products in used or mint condition, such as furniture, when moving out or simply looking to declutter. Buyers can browse a variety of options and find items available in their nearest neighborhoods, making it easy and convenient to acquire needed goods locally.

### App Evaluation

[Evaluation of your app across the following attributes]
- **Category:** Online Marketplace
- **Mobile:**(Essential) With a mobile version, sellers can list pickup locations for their items and allow buyers to search for products within a specified radius in their local area. The app features a camera function for uploading product images and uses GPS to share precise locations. Upon registering, users can create accounts as either buyers or sellers, including payment information. As a stretch goal, buyers and sellers can communicate via an in-app chat feature and push notifications will alert users when an item becomes available in their region.
- **Story:** Our app is the perfect tool for anyone moving in or out of a city, providing a simple platform to buy or sell used items quickly and affordably. Sellers can easily monetize unwanted items, while buyers enjoy local deals on a variety of products.
- **Market:** Anyone looking to buy or sell various items quickly and easily.
- **Habit:** Promoting sustainability by encouraging users to embrace thrifting culture and fostering a sustainable lifestyle mindset.
- **Scope:** 
    - V1 enables users to list items for sale, assuming either the role of seller or buyer. Buyers can view listings within their specified radius and connect with sellers using features like image uploads and GPS location.
    - V2 enhances user experience by facilitating communication between buyers and sellers directly through the app.
    - V3 further enriches the app by allowing users to send push notifications to buyers when a desired product becomes available in their region.

## Product Spec

### 1. User Features (Required and Optional)

**Required Features**

1. User can login/sign up in the app
2. User can take the role of either buyer or seller
3. User can update the profile information including contact number etc
4. If the role is **BUYER**:
    * User can see the product listings
    * User can apply several filters including search radius to see products in nearby places, tags based on product categories
    * User can see the descriptions of products including images, price etc
    * User can see the contact details of seller
5. If the role is **SELLER**:
    * User can post the products listings
    * User can post products images, price information and pickup details (address details -> app will extract lat and lng from that)
    * User will be charged a nominal amount to post listings
    * User can mark the product as sold if it is no longer available
    * User can associate product listings with some tags
    * User can see his/her product listings

**Optional Features**

1. Buyer and seller can chat with each other using the chat feature
2. App will send push notifications to the buyer if product becomes available in their region
3. Buyer can create a wishlist on the app to get push notifications

### 2. Screen Archetypes

- Login/ Signup screen
  - New users can register, add card details
  - Old users can login
- Product listings screen with filters button
  - If user is buyer: can see listings page
  - If user is seller: can see their listings
  - Buyer can see filters button to apply filters
- Filters Screen
    - User can apply various filters and on submit, go back to product listings screen
- Product description screen
    - User can see the product description including images, price info, seller details etc
    - Contact seller button which can open chat feature (in V2)
- Add product screen
    - Seller can add the product along with its description, images, price, pickup location(if its different from seller current location) etc
    - User can click on Submit button to store product in our database
- User profile details Screen
    - user can enter profile details, upadte them including contact information, location etc
    

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Listings
* Profile 

**Flow Navigation** (Screen to Screen)

- Login Screen
  - [x] User can go to listings screen when submit button is clicked
- Product listings screen with filters button
  - [x] User can go to filters screen on clicking filters button
  - [x] User can go to product details screen when "see product" button is clicked
  - [x]User can go to add product screen
- Filters Screen
    - [x] User can go back to product listings when filter popup is closed or filters form is submitted
    - User shuld be able to filter listing using firebase
- Product description screen
    - [x] User can go back to product listings screen with back button
    - User can go to the chat feature/ communicate with seller
    - Fetch data for each product from firebase
- Add product screen
    - [x] User can go back to main screen once product is added
    - Product should be added to the list and database when submit is clicked
- User profile details Screen
    - [x] User can go back to preferneces screen
    - [x] User can go back to homepage (Main product listing screen)
    - User should be able to update the profile in database


## Wireframes

<a href="https://ibb.co/bQgLCJf"><img src="https://i.ibb.co/4KZMXdQ/and1021.jpg" alt="and1021" border="0"></a>
<a href="https://ibb.co/HYCmLCN"><img src="https://i.ibb.co/RPNLGNh/and1022.jpg" alt="and1022" border="0"></a>

<br>

<br>

### [BONUS] Digital Wireframes & Mockups

##Figma prototype


<a href="https://www.figma.com/file/pRh5OtFxnUPM2o0gufdflc/Sustainify?type=design&node-id=0%3A1&mode=design&t=58PqRnP880iasYgR-1">Figma Prototype</a>

### [BONUS] Interactive Prototype


<br>

# Milestone 2 - Build Sprint 1 (Unit 8)

## GitHub Project board

[Add screenshot of your Project Board with three milestones visible in
this section]
<a href="https://ibb.co/D710RwB"><img src="https://i.ibb.co/GVdz5CZ/Screenshot-2024-04-19-at-3-57-19-PM.png" alt="Screenshot-2024-04-19-at-3-57-19-PM" border="0"></a> <br>
<a href="https://www.figma.com/embed?embed_host=share&url=https%3A%2F%2Fwww.figma.com%2Ffile%2FpRh5OtFxnUPM2o0gufdflc%2FSustainify%3Ftype%3Ddesign%26node-id%3D0%253A1%26mode%3Ddesign%26t%3DIdbOe4zfT9s1FqFF-1" allowfullscreen>**Figma Wireframes**</a>

## Issue cards

- [Add screenshot of your Project Board with the issues that you've been working on for this unit's milestone] <br><a href="https://ibb.co/gt0DH4B"><img src="https://i.ibb.co/HGcr1hk/Screenshot-2024-04-19-at-3-51-08-PM.png" alt="Screenshot-2024-04-19-at-3-51-08-PM" border="0"></a>
- [Add screenshot of your Project Board with the issues that you're working on in the **NEXT sprint**. It should include issues for next unit with assigned owners.]
<br>
<a href="https://imgbb.com/"><img src="https://i.ibb.co/myF4bBW/Screenshot-2024-04-19-at-3-56-10-PM.png" alt="Screenshot-2024-04-19-at-3-56-10-PM" border="0"></a> <br>
<a href="https://www.figma.com/embed?embed_host=share&url=https%3A%2F%2Fwww.figma.com%2Ffile%2FpRh5OtFxnUPM2o0gufdflc%2FSustainify%3Ftype%3Ddesign%26node-id%3D0%253A1%26mode%3Ddesign%26t%3DIdbOe4zfT9s1FqFF-1" allowfullscreen>**Figma Wireframes**</a>

## Issues worked on this sprint

- [Completed Issues] (User Profile Screen, Add Product Screen,Filters Screen,Product Details Screen,Product Listing Screen,Tab Navigations,Signup Screen UI,Login Screen UI)
- [Add giphy that shows current build progress for Milestone 2. Note: We will be looking for progression of work between Milestone 2 and 3. Make sure your giphys are not duplicated and clearly show the change from Sprint 1 to 2.]
  <a href="https://submissions.us-east-1.linodeobjects.com/and102/ol_0BN4G.gif" target="_blank">GIF</a>

<br>

# Milestone 3 - Build Sprint 2 (Unit 9)

## GitHub Project board
<a href="https://ibb.co/z8yYtzs"><img src="https://i.ibb.co/PT7nVdx/Screenshot-2024-04-26-at-10-30-49-PM.png" alt="Screenshot-2024-04-26-at-10-30-49-PM" border="0"></a>

## Completed user stories
- List the completed user stories from this unit
    - [x] Choose location from a map
    - [x] Update profile details, passwords, and card details
    - [x] Filter products by price range
- List any pending user stories / any user stories you decided to cut from the original requirements
    - [ ] Filter products by distance in miles
    - [ ] Chat with the seller in-app

## Progress GIFs
![Progress Gif](https://github.com/And102Group16/sustainable-marketplace/blob/firebase-filter/Milestone%203%20-%20Project%20Progress.gif)

![Progress Gif](https://github.com/And102Group16/sustainable-marketplace/blob/firebase-filter/Milestone%203%20-%20Progress%202%20Profile%20Update.gif)


## App Demo Video
