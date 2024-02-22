package com.gdscedirne.toplan.common

import com.gdscedirne.toplan.components.Screen
import com.gdscedirne.toplan.presentation.notification.NotificationItem

object Constants {

    val bottomNavItems = listOf(
        Screen.Home,
        Screen.Chat,
        Screen.Empty,
        Screen.Profile,
        Screen.Settings
    )

    const val ARGS_OPTION = "option"

    const val GEMINI_MODEL = "gemini-pro"

    const val USER_POLICY =
        "TopLan Mobile Application User Policy\n\nWelcome to TopLan!\n\nThis User Policy governs your access to and use of TopLan (the \"Application\"), including any content, functionality, and services offered on or through the Application. Please read this User Policy carefully before using the Application.\n\n1.\u2060 \u2060Acceptance of Terms\n\nBy downloading, accessing, or using the TopLan Application, you agree to be bound by this User Policy. If you do not agree with any part of this User Policy, you must not use the TopLan Application.\n\n2.\u2060 \u2060Privacy\n\nYour privacy is important to us. Please review our Privacy Policy [hyperlink to Privacy Policy] to understand how we collect, use, and disclose information about you when you use the TopLan Application. By using the TopLan Application, you consent to the collection, use, and disclosure of your information as described in the Privacy Policy.\n\n3.\u2060 \u2060Account Registration\n\nSome features of the TopLan Application may require you to register for an account. When registering for an account, you agree to provide accurate, current, and complete information. You are responsible for maintaining the confidentiality of your account credentials and for all activities that occur under your account.\n\n4.\u2060 \u2060User Conduct\n\nYou agree to use the TopLan Application only for lawful purposes and in accordance with this User Policy. You agree not to:\n\nViolate any applicable laws or regulations;\nUse the TopLan Application in any way that could harm, disable, overburden, or impair the TopLan Application or interfere with any other party's use of the TopLan Application;\nAttempt to gain unauthorized access to any part of the TopLan Application or any other systems or networks connected to the TopLan Application;\nUse any automated means to access the TopLan Application or collect information from the TopLan Application;\nTransmit any viruses, worms, defects, or other items of a destructive nature through the TopLan Application; or\nEngage in any conduct that restricts or inhibits anyone's use or enjoyment of the TopLan Application.\n5.\u2060 \u2060Intellectual Property\n\nThe TopLan Application and its entire contents, features, and functionality (including but not limited to all information, software, text, displays, images, video, and audio, and the design, selection, and arrangement thereof) are owned by GDSC Edirne or its licensors and are protected by copyright, trademark, patent, trade secret, and other intellectual property or proprietary rights laws.\n\n6.\u2060 \u2060Feedback\n\nWe welcome your feedback, comments, and suggestions for improvements to the TopLan Application. You agree that any feedback you provide may be used by us without any restriction or obligation to you.\n\n7.\u2060 \u2060Termination\n\nWe reserve the right to terminate or suspend your access to the TopLan Application without prior notice or liability for any reason, including if you violate this User Policy.\n\n8.\u2060 \u2060Changes to User Policy\n\nWe may revise and update this User Policy from time to time in our sole discretion. All changes are effective immediately when we post them and apply to all access to and use of the TopLan Application thereafter. Your continued use of the TopLan Application following the posting of revised User Policy means that you accept and agree to the changes.\n\n9.\u2060 \u2060Contact Us\n\nIf you have any questions about this User Policy, please contact us at \n\nmertozankahraman@gmail.com "

    const val PRIVACY_POLICY =
        "TopLan Mobile Application Privacy Policy\n\nWelcome to TopLan!\n\nThis Privacy Policy describes how we collect, uses, and shares information about you when you use the TopLan mobile application. By using the Application, you consent to the practices described in this Privacy Policy.\n\n1.\u2060 \u2060Information We Collect\n\na. Information You Provide\n\nAccount Information: When you register for an account on the TopLan Application, we collect information such as your name, email address, and password.\n\nb. Information We Collect Automatically\n\nUsage Information: We collect information about your use of the TopLan Application, including the features you access and how you interact with the Application.\nDevice Information: We collect information about the device you use to access the TopLan Application, including the device type, operating system, and unique device identifiers.\n\n2.\u2060 \u2060How We Use Your Information\n\nWe may use the information we collect for various purposes, including to:\n\nProvide, maintain, and improve the TopLan Application;\nCommunicate with you about the TopLan Application;\nPersonalize your experience on the TopLan Application;\nAnalyze trends and user behavior on the TopLan Application;\nDetect, investigate, and prevent fraudulent or unauthorized activity on the TopLan Application;\nComply with legal obligations; and\nFulfill any other purpose disclosed to you at the time we collect your information or with your consent.\n\n3.\u2060 \u2060Information Sharing\n\nWe may share your information as follows:\n\nWith service providers who help us operate the TopLan Application and provide services related to the Application;\nWith third parties with whom you choose to share information through the TopLan Application;\nIn response to a request for information if we believe disclosure is in accordance with any applicable law, regulation, or legal process;\nIf we believe your actions are inconsistent with the spirit or language of our user agreements or policies, or to protect the rights, property, and safety of GDSC Edirne, our users, or others;\nIn connection with, or during negotiations of, any merger, sale of company assets, financing, or acquisition of all or a portion of our business by another company; and\nWith your consent or at your direction.\n\n4.\u2060 \u2060Data Security\n\nWe take reasonable measures to help protect your information from loss, theft, misuse, and unauthorized access, disclosure, alteration, and destruction.\n\n5.\u2060 \u2060Children's Privacy\n\nThe TopLan Application is not intended for use by children under the age of 8. We do not knowingly collect personal information from children under the age of 8. If you are under the age of 8, please do not use the TopLan Application or provide any information to us.\n\n6.\u2060 \u2060Your Choices\n\nYou may update or delete your account information at any time by accessing your account settings within the TopLan Application.\n\n7.\u2060 \u2060Changes to This Privacy Policy\n\nWe may update this Privacy Policy from time to time. If we make material changes to this Privacy Policy, we will notify you by posting a notice on the TopLan Application or by other means.\n\n8.\u2060 \u2060Contact Us\n\nIf you have any questions about this Privacy Policy, please contact us via \n\nmertozankahraman@gmail.com"

    val notificationList: List<NotificationItem> = listOf(
        NotificationItem(
            id = "1",
            title = "Remember the facts",
            content = "Do you have an earthquake kit for earthquake hazard?"
        ),
        NotificationItem(
            id = "2",
            title = "Be careful!",
            content = "Anything can happen at any time. Don't forget to take precautions"
        ),
        NotificationItem(
            id = "3",
            title = "Did you know?",
            content = "Did you know that the first earthquake in the world was in China?"
        ),
        NotificationItem(
            id = "4",
            title = "Hurricane Safety",
            content = "Review your hurricane preparedness plan. Stay safe and informed."
        ),
        NotificationItem(
            id = "5",
            title = "Wildfire Awareness",
            content = "Check for wildfire alerts in your area. Follow evacuation guidelines if necessary."
        ),
        NotificationItem(
            id = "6",
            title = "Earthquake Preparedness",
            content = "Ensure you have an earthquake kit with essential supplies."
        ),
        NotificationItem(
            id = "7",
            title = "Stay Alert!",
            content = "Be vigilant! Unexpected events can occur. Take necessary precautions."
        ),
        NotificationItem(
            id = "8",
            title = "Facts About Earthquakes",
            content = "Did you know that the Richter scale measures the magnitude of earthquakes?"
        ),
    )

}