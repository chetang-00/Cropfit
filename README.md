# CropFit - Plant Disease Detection Android App

A comprehensive Android application that combines plant disease detection using machine learning.

## Features

### Machine Learning Capabilities
- **Real-time Plant Disease Detection** using TensorFlow Lite
- **Camera Integration** for instant disease diagnosis
- **Gallery Image Analysis** for existing photos
- **Disease Classification**: Blight, Common Rust, Gray Leaf Spot, Healthy plants
- **Detailed Disease Information** with symptoms and treatment recommendations

### E-commerce Platform
- **Product Catalog** with agricultural supplies
- **Shopping Cart** functionality
- **Secure Payment Processing**
- **Order Management** and tracking
- **User Profile Management**

### User Management
- **Firebase Authentication** (Email/Password, Google Sign-in)
- **User Registration** and profile creation
- **Appointment Booking** system
- **Password Recovery** functionality

## Technology Stack

- **Frontend**: Android Native (Java)
- **Backend**: Firebase (Authentication, Realtime Database)
- **Machine Learning**: TensorFlow Lite 2.1.0
- **Image Processing**: CameraKit, Picasso
- **UI Components**: Material Design, ConstraintLayout
- **Build System**: Gradle

## Prerequisites

- Android Studio Arctic Fox or later
- Android SDK (API level 22+)
- Google Firebase account
- TensorFlow Lite model file

## Installation

### 1. Clone the Repository
```bash
git clone repo
```

### 2. Firebase Setup
1. Create a new Firebase project at [Firebase Console](https://console.firebase.google.com/)
2. Enable Authentication (Email/Password, Google Sign-in)
3. Create a Realtime Database
4. Download your `google-services.json` file
5. Place it in the `app/` directory

### 3. TensorFlow Model Setup
1. Ensure the `plant_disease_tfmodel.tflite` file is in `app/src/main/assets/`
2. Verify `plant_labels.txt` contains the disease classifications

### 4. Build and Run
```bash
# Using Gradle wrapper
./gradlew build
./gradlew installDebug

# Or open in Android Studio and run
```


## Configuration

### Firebase Configuration
Create a `google-services.json` file in the `app/` directory with your Firebase project credentials:

```json
{
  "project_info": {
    "project_number": "YOUR_PROJECT_NUMBER",
    "project_id": "YOUR_PROJECT_ID",
    "storage_bucket": "YOUR_PROJECT_ID.appspot.com"
  },
  "client": [
    {
      "client_info": {
        "mobilesdk_app_id": "YOUR_APP_ID",
        "android_client_info": {
          "package_name": "com.example.cropfit"
        }
      },
      "oauth_client": [
        {
          "client_id": "YOUR_CLIENT_ID",
          "client_type": 1,
          "android_info": {
            "package_name": "com.example.cropfit",
            "certificate_hash": "YOUR_CERTIFICATE_HASH"
          }
        }
      ],
      "api_key": [
        {
          "current_key": "YOUR_API_KEY"
        }
      ]
    }
  ]
}
```

## Testing

### Plant Disease Detection
1. Open the app and navigate to Detection
2. Take a photo or select from gallery
3. The app will analyze and classify the plant disease
4. View detailed symptoms and treatment recommendations

### E-commerce Features
1. Register/Login to your account
2. Browse products in the shop
3. Add items to cart and complete purchase
4. Track orders and manage profile


**Note**: This project requires the TensorFlow Lite model file (`plant_disease_tfmodel.tflite`) which is not included in the repository due to size limitations. Please obtain the model file separately or contact the author.
