<?php

namespace App\Form;

use App\Entity\Reclamation;
use App\Entity\Reponse;
use Doctrine\ORM\EntityRepository;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ReponseType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('contenu')
            ->add('reclamation',EntityType::class, [
                'class' => Reclamation::class,
                'placeholder' => "Reclamation",
                'query_builder' => function (EntityRepository $er) {
                    return $er->createQueryBuilder('a')
                        ->orderBy('a.id', 'ASC');
                },
                'choice_label' => function (Reclamation $reclamation) {
                    // Assuming 'client' is the property in Compte entity that references the Client entity.
                    // Adjust this based on your actual entity structure.
                    return $reclamation->getName();
                },
                'required' => false,
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Reponse::class,
        ]);
    }
}
