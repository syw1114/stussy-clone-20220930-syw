package com.stussy.stussyclone20220930syw.controller.dto.validation;


import javax.validation.GroupSequence;
import javax.validation.groups.Default;


//순번이다  블랭크검사하고 사이즈검사하고 패턴검사해라.
@GroupSequence({ValidationGroups.NotBlankGroup.class,
        ValidationGroups.SizeGroup.class,
        ValidationGroups.PatternCheckGroup.class,
        Default.class
})
public class ValidationSequence {

}
