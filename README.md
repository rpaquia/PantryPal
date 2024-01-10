# PantryPal Team 23


Clone the repo

Create .vscode folder in root directory

In .vscode folder, create launch.json and paste the following, ensure you change your javaFX lib file path

	{

		"version": "0.2.0",
		
		"configurations": [

			{

				"type": "java",

				"name": "PantryPal",

				"request": "launch",

				"mainClass": "client.PantryPal",

				"vmArgs": "--module-path <path> --add-modules javafx.controls,javafx.fxml"

			},

			{

				"type": "java",

				"name": "Server",

				"request": "launch",

				"mainClass": "server.Server",

			},

		]

	}

	  

In .vscode folder, create settings.json file, and paste the following code

	{

		"java.configuration.updateBuildConfiguration": "automatic",

		"java.compile.nullAnalysis.mode": "automatic"

	}

In the Run and Debug panel, run server and then run PantryPal

please don't use my API key :\)