package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueLight;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedLight;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        // Declare our first bot
        RoadRunnerBotEntity myFirstBot = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myFirstBot.runAction(myFirstBot.getDrive().actionBuilder(new Pose2d(23.7, 69, Math.toRadians(270)))
                .splineToLinearHeading(new Pose2d(63.7,69,Math.toRadians(90)),0)
                .splineToLinearHeading(new Pose2d(58.5,24.3,Math.toRadians(270)),0)
                .splineToLinearHeading(new Pose2d(63.7,69,Math.toRadians(90)),0)
                .build());

        // Declare out second bot
        RoadRunnerBotEntity mySecondBot = new DefaultBotBuilder(meepMeep)
                // We set this bot to be red
                .setColorScheme(new ColorSchemeBlueLight())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        mySecondBot.runAction(mySecondBot.getDrive().actionBuilder(new Pose2d(-23.7, 69, Math.toRadians(270)))
                .splineToLinearHeading(new Pose2d(-3,23,Math.toRadians(270)),0)
                .splineToLinearHeading(new Pose2d(-47.3,48,Math.toRadians(270)), 0)
                .splineToLinearHeading(new Pose2d(-48.3,24.8,270),0)
                .splineToLinearHeading(new Pose2d(-64.9,65.8,Math.toRadians(135)),0)
                .build());

        RoadRunnerBotEntity myThirdBot = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myThirdBot.runAction(myFirstBot.getDrive().actionBuilder(new Pose2d(23.7, -69, Math.toRadians(270)))
                .waitSeconds(1)
                .splineToLinearHeading(new Pose2d(7.3,-23.1,Math.toRadians(90)),0)
                .splineToLinearHeading(new Pose2d(31,-40,Math.toRadians(90)),0)
                .splineToLinearHeading(new Pose2d(58.5,-24.3,Math.toRadians(270)),0)
                .splineToLinearHeading(new Pose2d(63.7,-69,Math.toRadians(270)),0)
                .build());

        RoadRunnerBotEntity myForthBot = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedLight())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myForthBot.runAction(myForthBot.getDrive().actionBuilder(new Pose2d(-23.7, -69, Math.toRadians(90)))
                .splineToLinearHeading(new Pose2d(-3,-23,Math.toRadians(90)),0)
                .splineToLinearHeading(new Pose2d(-47.3,-48,Math.toRadians(90)), 0)
                .splineToLinearHeading(new Pose2d(-48.3,-24.8,90),0)
                .splineToLinearHeading(new Pose2d(-64.9,-65.8,Math.toRadians(-45)),0)
                .build()
        );
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                // Add both of our declared bot entities
                .addEntity(myFirstBot)
                .addEntity(mySecondBot)
                .addEntity(myThirdBot)
                .addEntity(myForthBot)
                .start();
    }
}