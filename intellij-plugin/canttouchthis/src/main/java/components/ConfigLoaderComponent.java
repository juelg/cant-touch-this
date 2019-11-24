package components;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import data.Config;
import data.DataLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.Collectors;

// TODO set to reloadable and handle changes
@State(name = "Config", reloadable = false)
public class ConfigLoaderComponent implements PersistentStateComponent<data.Config> {
    @Nullable
    @State(name = "config")
    public Config state;

    public DataLoader dataLoader;

    @Nullable
    @Override
    public Config getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull Config state) {
        System.out.println("Config loaded with values: " + state.user + ", " + state.host);
        this.state = state;
        this.dataLoader= new DataLoader(state);
    }

    @Override
    public void noStateLoaded() {
        System.out.println("No config found");
        this.state = new Config();
        this.state.user = "";
        this.state.host = "";
    }

    @Override
    public void initializeComponent() {
        String pythonPath = "/home/tobi/coding/HackaTUM19/cant-touch-this/client/dont_touch.py";
        String pythonEnvPath = "/home/tobi/coding/HackaTUM19/cant-touch-this/backend/venv3.7/bin/python";
        String topLevel = "/home/tobi/coding/HackaTUM19/cant-touch-this/";
        Runtime rt = Runtime.getRuntime();
        Runtime rt2 = Runtime.getRuntime();
        try {
            Process pr2 = rt2.exec(pythonEnvPath + " " + pythonPath + " --path " + topLevel);
            Process pr = rt.exec("git rev-parse --show-toplevel");
            String result = new BufferedReader(new InputStreamReader(pr.getInputStream()))
                    .lines().collect(Collectors.joining("\n"));
            System.out.println(result);
            if(!result.equals("")){
                topLevel = result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
