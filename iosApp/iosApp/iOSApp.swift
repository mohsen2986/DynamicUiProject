import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        DiKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
