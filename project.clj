(defproject illithid "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha14"]
                 [org.clojure/clojurescript "1.9.293"]
                 [org.clojure/test.check "0.9.0"]
                 [org.clojure/core.match "0.3.0-alpha4"]
                 [better-cond "1.0.1"]
                 [com.gfredericks/test.chuck "0.2.7"]
                 [reagent "0.6.0" :exclusions [cljsjs/react
                                               cljsjs/react-dom
                                               cljsjs/react-dom-server]]
                 [re-frame "0.8.0"]
                 [core-async-storage "0.2.0"]
                 [org.clojure/core.async "0.2.395"]
                 [com.taoensso/encore "2.88.2"]]
  :plugins [[lein-cljsbuild "1.1.4"]
            [lein-figwheel "0.5.8"]
            [lein-doo "0.1.7"]]
  :resource-paths ["resources"]
  :clean-targets ["target/" "index.ios.js" "index.android.js"]
  :aliases {"prod-build" ^{:doc "Recompile code with prod profile."}
            ["do" "clean"
             ["with-profile" "prod" "cljsbuild" "once" "ios"]
             ["with-profile" "prod" "cljsbuild" "once" "android"]]}
  :profiles
  {:dev {:dependencies
         [[figwheel-sidecar "0.5.8"]
          [com.cemerick/piggieback "0.2.1"]]
         :source-paths ["src" "env/dev"]

         :cljsbuild
         {:builds
          {:ios
           {:source-paths ["src" "env/dev"]
            :figwheel     true
            :compiler
            {:output-to     "target/ios/not-used.js"
             :main          "env.ios.main"
             :output-dir    "target/ios"
             :optimizations :none
             :warnings {:munged-namespace false}}}

           :android
           {:source-paths ["src" "env/dev"]
            :figwheel     true
            :compiler
            {:output-to     "target/android/not-used.js"
             :main          "env.android.main"
             :output-dir    "target/android"
             :optimizations :none
             :warnings {:munged-namespace false}}}

           :test
           {:source-paths ["src" "env/dev" "test"]
            :compiler
            {:output-to "target/test/main.js"
             :output-dir "target/test"
             :main illithid.runner
             :optimizations :none
             :warnings {:munged-namespace false}}}}}
         :repl-options
         {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}

   :prod
   {:cljsbuild
    {:builds
     {:ios
      {:source-paths ["src" "env/prod"]
       :compiler     {:output-to     "index.ios.js"
                      :main          "env.ios.main"
                      :output-dir    "target/ios"
                      :optimizations :simple
                      :warnings {:munged-namespace false}}}
      :android
      {:source-paths ["src" "env/prod"]
       :compiler     {:output-to     "index.android.js"
                      :main          "env.android.main"
                      :output-dir    "target/android"
                      :optimizations :simple
                      :warnings {:munged-namespace false}}}}}}})
